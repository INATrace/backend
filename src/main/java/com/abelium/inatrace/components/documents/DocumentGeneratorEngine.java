package com.abelium.inatrace.components.documents;

import com.abelium.inatrace.tools.documents.DocumentForm;
import com.abelium.inatrace.tools.documents.DocxDocumentCreator;
import com.abelium.inatrace.tools.documents.PdfTools;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Lazy
@Service
public class DocumentGeneratorEngine 
{
    @Value("${INATrace.documents.root}")
    private String root;
    
    public byte[] getPdfDocument(DocumentForm form) throws IOException {
        byte[] result;
        
        try (DocxDocumentCreator creator = new DocxDocumentCreator(root)) {
            XWPFDocument doc = creator.fill(form);
            result = PdfTools.convertDocxToPdf(doc);
        }
        
        return result;
    }
   
}
