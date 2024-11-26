package com.abelium.inatrace.tools.documents;

import fr.opensagres.poi.xwpf.converter.core.XWPFConverterException;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfTools {
    
    private static final PdfFontProvider fontProvider = new PdfFontProvider();
    
    public static byte[] getDocx(XWPFDocument document) throws XWPFConverterException, IOException {
        byte[] docx;
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
        byte[] result;
        
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            PdfOptions options = PdfOptions.create();
            options.fontProvider(fontProvider);
            XWPFDocument docx = new XWPFDocument(new ByteArrayInputStream(document));
            PdfConverter.getInstance().convert(docx, stream, options);
            result = stream.toByteArray();
        }
        
        return result;
    }
    
}
