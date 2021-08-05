package com.abelium.inatrace.tools.documents;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;

public class PdfFontProvider extends ITextFontRegistry {
    
    public static HashSet<String> fonts = new HashSet<String>(); // temporary
    
    public PdfFontProvider() {
        FontFactory.registerDirectory("fonts", true);
    }
    
    private Map<String, BaseFont> baseFonts = new HashMap<>();
    
    @Override
    public Font getFont(String familyName, String encoding, float size, int style, Color color) {
        fonts.add(familyName);
        try {
            if (familyName.equalsIgnoreCase("ARIAL NARROW") || familyName.equalsIgnoreCase("Calibri")) {
                // replaces ARIAL NARROW with ARCHIVO NARROW
                
                BaseFont baseFont = null;
                
                String fontKey = getFontKey(familyName, style);
                if (!baseFonts.containsKey(fontKey)) {
                    switch (style) {
                        case Font.BOLD:
                            baseFont = BaseFont.createFont("fonts//archivo//ArchivoNarrow-Bold.otf", encoding, BaseFont.EMBEDDED);
                            break;
                        case Font.ITALIC:
                            baseFont = BaseFont.createFont("fonts//archivo//ArchivoNarrow-Italic.otf", encoding, BaseFont.EMBEDDED);
                            break;
                        case Font.BOLDITALIC:
                            baseFont = BaseFont.createFont("fonts//archivo//ArchivoNarrow-BoldItalic.otf", encoding, BaseFont.EMBEDDED);
                            break;
                        default:
                            baseFont = BaseFont.createFont("fonts//archivo//ArchivoNarrow-Regular.otf", encoding, BaseFont.EMBEDDED);;
                            break;
                    }
                    baseFonts.put(fontKey, baseFont);
                }
                
                return new Font(baseFonts.get(fontKey), size, style, color);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return super.getFont(familyName, encoding, size, style, color);
    }
    
    private String getFontKey(String familyName, int style) {
        return String.format("%s-%d", familyName, style);
    }
    
}
