package com.abelium.INATrace.components.product;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.BaseEngine;
import com.abelium.INATrace.components.documents.DocumentGeneratorEngine;
import com.abelium.INATrace.db.entities.ProductLabel;
import com.abelium.INATrace.security.service.CustomUserDetails;
import com.abelium.INATrace.tools.documents.Bookmark;
import com.abelium.INATrace.tools.documents.DocumentForm;
import com.abelium.INATrace.types.MediaObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Lazy
@Component
public class ProductDocumentEngine extends BaseEngine {
	
	static final class LabelInstructionsForm implements DocumentForm {
		
		@Bookmark("label_name")
		public String labelName;
		
		@Bookmark("product_name")
		public String productName;
		
		public LabelInstructionsForm(String labelName, String productName) {
			this.labelName = labelName;
			this.productName = productName;
		}

		@Override
	    public String getTemplatePath() {
	        return "INATrace_labels_text.docx";
	    }  		
	}
	
    @Value("${INATrace.documents.root}")
    private String documentsRoot;	

    @Value("${INATrace.fe.url}")
    private String feUrlPrefix;	
    
    
	@Autowired
	private ProductQueries productQueries;
	
    @Autowired
    private DocumentGeneratorEngine documentEngine;
    
    @Transactional
    public MediaObject createLabelsAndHowToUseInstructions(CustomUserDetails authUser, Long labelId) throws ApiException {
    	ProductLabel l = productQueries.fetchProductLabelAssoc(authUser, labelId);
    	
    	try {
    		// Original document
			byte[] document = documentEngine.getPdfDocument(new LabelInstructionsForm(l.getTitle(), l.getProduct().getName()));
	    	
			// Overlay docdument
			Path path = Paths.get(documentsRoot, "INATrace-labele-o-A4.pdf");
	    	PDDocument overlayPdf = PDDocument.load(path.toFile());
	    	
	    	// Add images to overlay
	    	addImages(overlayPdf, feUrlPrefix + "q/" + l.getUuid());
	    		    	
	    	// Put overlay
			document = putOverlay(document, overlayPdf);
			return new MediaObject(MediaType.APPLICATION_PDF, document);
		} catch (Exception e) {
			logger.error("Error creating instructions document", e);
			throw new ApiException(ApiStatus.ERROR, "Error creating instructions document");
		}
    	
    }
    
    private byte[] putOverlay(byte[] origDocument, PDDocument overlayPdf) throws Exception, Exception {
    	PDDocument orig = PDDocument.load(origDocument);
    	try (Overlay overlay = new Overlay()) {
	    	overlay.setInputPDF(orig);
	    	overlay.setOverlayPosition(Overlay.Position.FOREGROUND);
	    	overlay.overlayDocuments(Map.of(1, overlayPdf));
    	}
    	try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {    	
    		orig.save(stream);
    		return stream.toByteArray();
    	}
    }
    
    private static final int DEFAULT_USER_SPACE_UNIT_DPI = 72;
    private static final float MM_TO_UNITS = 1 / (10 * 2.54f) * DEFAULT_USER_SPACE_UNIT_DPI;
    
    private void addImages(PDDocument doc, String qrCodeText) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix qrCode = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 350, 350, Map.of(EncodeHintType.MARGIN, 0));
        byte[] img;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) { 
            MatrixToImageWriter.writeToStream(qrCode, "png", baos);
            img = baos.toByteArray();
        }
        
        PDPage page = doc.getPage(0);        
        PDImageXObject qrImage = PDImageXObject.createFromByteArray(doc, img, "qr-code");
        PDPageContentStream contents = new PDPageContentStream(doc, page, AppendMode.APPEND, true, false);      
        for (int i = 0; i < 5; i++) {
            contents.drawImage(qrImage, (41.175f * i + 10) * MM_TO_UNITS, 185.5f * MM_TO_UNITS, 25 * MM_TO_UNITS, 25 * MM_TO_UNITS);
        }
        contents.close();
    }
    
}
