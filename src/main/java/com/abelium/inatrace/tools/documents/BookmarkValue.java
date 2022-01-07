package com.abelium.inatrace.tools.documents;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;

public class BookmarkValue {
    
    private List<Paragraph> paragraphs = new ArrayList<>();
    
    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }
    
    public Paragraph addParagraph() {
        Paragraph p = new Paragraph();
        paragraphs.add(p);
        return p;
    }
    
    public static BookmarkValue fromString(String text) {
        BookmarkValue v = new BookmarkValue();
        for (String line : StringUtils.split(text, '\n')) {
            Paragraph p = v.addParagraph();
            p.addPart(line);
        }
        return v;
    }
    
    
    public static class Paragraph {
        
        private List<Part> parts = new ArrayList<>();
        
        public List<Part> getParts() {
            return parts;
        }
        
        public void addPart(String text) {
            addPart(text, null);
        }

        public void addPart(String text, String fontFamily) {
            parts.add(new Part(text, fontFamily));
        }
        
        public void addHyperlinkPart(String url, String text) {
            addHyperlinkPart(url, text, null);
        }
        
        public void addHyperlinkPart(String url, String text, String fontFamily) {
            if (StringUtils.isNotBlank(url)) {
                parts.add(new HyperlinkPart(url, text, fontFamily));
            } else {
               addPart(text, fontFamily);
            }
        }
        
        public static class Part {
            
            protected String text;
            protected String fontFamily;
            
            public Part(String text, String fontFamily) {
                this.text = text;
                this.fontFamily = fontFamily;
            }
            
            public String getValue() {
                return text;
            }
            
            public String getFontFamily() {
                return fontFamily;
            }

            public XWPFRun createRun(XWPFParagraph par) {
                XWPFRun run = par.createRun();
                run.setText(text);
                if (fontFamily != null) {
                    run.setFontFamily(fontFamily);
                }
                return run;
            }
            
        }
        
        public static class HyperlinkPart extends Part {
            
            static XWPFHyperlinkRun createHyperlinkRun(XWPFParagraph paragraph, String uri) {
                String rId = paragraph.
                        getDocument().
                        getPackagePart().
                        addExternalRelationship(uri, XWPFRelation.HYPERLINK.getRelation()).getId();

                CTHyperlink cthyperLink = paragraph.getCTP().addNewHyperlink();
                cthyperLink.setId(rId);
                cthyperLink.addNewR();

                return new XWPFHyperlinkRun(cthyperLink, cthyperLink.getRArray(0), paragraph);
            }
            
            protected String uri;
            
            public HyperlinkPart(String uri, String text, String fontFamily) {
                super(text, fontFamily);
                this.uri = uri;
            }
            
            public XWPFRun createRun(XWPFParagraph par) {
                XWPFRun run = createHyperlinkRun(par, uri);
                run.setText(text);
                run.setColor("0000FF");
                if (fontFamily != null) {
                    run.setFontFamily(fontFamily);
                }
                return run;
            }
            
        }
        
    }
    
}
