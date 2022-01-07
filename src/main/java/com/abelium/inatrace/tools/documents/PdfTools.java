package com.abelium.inatrace.tools.documents;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import fr.opensagres.poi.xwpf.converter.core.XWPFConverterException;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

public class PdfTools {
    
    private static PdfFontProvider fontProvider = new PdfFontProvider();
    
    public static byte[] getDocx(XWPFDocument document) throws XWPFConverterException, IOException {
        byte[] docx = null;
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            document.write(stream);
            docx = stream.toByteArray();
        }
        return docx;
    }
    
  
    public static byte[] convertDocxToPdf(XWPFDocument document) throws XWPFConverterException, IOException {
        return convertDocxToPdf( getDocx(document) );
    }
    
    public static byte[] convertDocxToPdf(byte[] document) throws XWPFConverterException, IOException {
        byte[] result = null;
        
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            // PdfFontProvider.fonts.clear();
            PdfOptions options = PdfOptions.create();
            options.fontProvider(fontProvider);
            XWPFDocument docx = new XWPFDocument(new ByteArrayInputStream(document));
            PdfConverter.getInstance().convert(docx, stream, options);
            // System.out.println(PdfFontProvider.fonts);
            result = stream.toByteArray();
        }
        
        return result;
    }
    
    public static byte[] convertImageToPdf(byte[] image) throws IOException {
        byte[] result = null;
        
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(doc, image, "");
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            
            // fit image to page
            PDRectangle mediaBox = page.getMediaBox();
            float ratio = (float)pdImage.getWidth() / pdImage.getHeight();
            float pageRatio = mediaBox.getWidth() / mediaBox.getHeight();
            float width, height;
            if (ratio > pageRatio) {
                width = mediaBox.getWidth();
                height = (int)Math.floor(width / ratio);
            } else {
                height = mediaBox.getHeight();
                width = (int)Math.floor(height * ratio);
            }
            float startX = (mediaBox.getWidth() - width) / 2;
            float startY = (mediaBox.getHeight() - height) / 2;
            
            contents.drawImage(pdImage, startX, startY, width, height);
            contents.close();
            
            doc.save(stream);
            result = stream.toByteArray();
        }
        
        return result;
    }
    
    public static byte[] emptyPage() throws IOException {
        return emptyA4(false);
    }
    
    public static byte[] emptyA4(boolean rotated) throws IOException {
        byte[] result = null;
        
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            if (rotated) {
                page.setRotation(90);
            }
            doc.addPage(page);
            doc.save(stream);
            result = stream.toByteArray();
        }
        
        return result;
    }
    
    public static byte[] mergePdfs(List<byte[]> sources) throws IOException {
        byte[] result = null;
        
        if (sources.size() == 1) {
            return sources.get(0);
        } else if (sources.size() > 1) {
            try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                PDFMergerUtility u = new PDFMergerUtility();
                u.addSources(sources.stream().map(s -> new ByteArrayInputStream(s)).collect(Collectors.toList()));
                u.setDestinationStream(stream);
                u.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
                result = stream.toByteArray();
            }
        }
        
        return result;
    }
    
    public static byte[] disableEditing(byte[] source) throws IOException {
        byte[] result;
        
        try (PDDocument document = PDDocument.load(source); ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            AccessPermission ap = new AccessPermission();
            ap.setCanModify(false);
            ap.setCanModifyAnnotations(false);
            ap.setCanFillInForm(false);
            ap.setCanAssembleDocument(false);
            ap.setCanExtractContent(false);
            
            StandardProtectionPolicy spp = new StandardProtectionPolicy(UUID.randomUUID().toString(), "", ap);
            spp.setEncryptionKeyLength(128);
            
            document.protect(spp);
            document.save(stream);
            
            result = stream.toByteArray();
        }
        
        return result;
    }
    
}
