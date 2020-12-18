package com.abelium.INATrace.components.documents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.abelium.INATrace.tools.documents.DocumentForm;
import com.abelium.INATrace.tools.documents.DocxDocumentCreator;
import com.abelium.INATrace.tools.documents.PdfTools;

import fr.opensagres.poi.xwpf.converter.core.XWPFConverterException;

@Lazy
@Service
public class DocumentGeneratorEngine 
{
    @Value("${INATrace.documents.root}")
    private String root;
    
    
    public byte[] getDocxDocument(DocumentForm form) throws IOException {
        byte[] result = null;
        
        try (DocxDocumentCreator creator = new DocxDocumentCreator(root)) {
            creator.fill(form);
            result = creator.toByteArray();
        }
        
        return result;
    }
    
    public <T extends DocumentForm> byte[] getPdfDocument(List<T> forms) throws IOException {
        byte[] result = null;
        
        List<byte[]> pdfs = new ArrayList<>();
        for (DocumentForm form : forms) {
            pdfs.add(getPdfDocument(form));
        }
        if (pdfs.size() == 1) {
            result = pdfs.get(0);
        } else {
            result = PdfTools.mergePdfs(pdfs);
        }
        
        return result;
    }
    
    public byte[] getPdfDocument(DocumentForm form) throws IOException {
        byte[] result = null;
        
        try (DocxDocumentCreator creator = new DocxDocumentCreator(root)) {
            XWPFDocument doc = creator.fill(form);
            result = PdfTools.convertDocxToPdf(doc);
        }
        
        return result;
    }
    
    public byte[] convertDocxToPdf(byte[] docx) throws XWPFConverterException, IOException {
        return PdfTools.convertDocxToPdf(docx);        
    }
   
}
