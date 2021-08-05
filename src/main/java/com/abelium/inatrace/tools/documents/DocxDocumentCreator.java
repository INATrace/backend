package com.abelium.inatrace.tools.documents;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ISDTContents;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFSDTContent;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAltChunk;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.w3c.dom.Node;

import com.abelium.inatrace.tools.documents.BookmarkValue.Paragraph;
import com.abelium.inatrace.tools.documents.BookmarkValue.Paragraph.Part;


public class DocxDocumentCreator implements Closeable {
    
    private String root;
    private XWPFDocument doc;
    
    public DocxDocumentCreator(String root) {
        this.root = root;
    }
    
    public XWPFDocument fill(DocumentForm form) throws FileNotFoundException, IOException {
        try (InputStream stream = form.getInputStream(root)) {
            doc = new XWPFDocument(stream);
            BookmarkMapGenerator generator = new BookmarkMapGenerator().addFromObject(doc, form);
            replaceBookmarks(doc, generator.get(), generator.getHtml());
        }
        return doc;
    }
    
    public void write(OutputStream stream) throws IOException {
        doc.write(stream);
    }
    
    public byte[] toByteArray() throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            write(stream);
            return stream.toByteArray();
        }
    }
    
    private void replaceBookmarks(XWPFDocument doc, Map<String, BookmarkValue> bookmarkMap, Map<String, BookmarkHtmlValue> htmlBookmarkMap) {
    	for (XWPFTable table : getDocumentTables(doc)) {
    		replaceBookmarksInTable(table, bookmarkMap, htmlBookmarkMap);
    	}
    	
    	for (XWPFParagraph paragraph : getDocumentParagraphs(doc)) {
    	    replaceBookmarksInParagraph(paragraph, bookmarkMap, htmlBookmarkMap);
    	}
    }
    
    @SuppressWarnings("unchecked")
    private List<XWPFTable> getDocumentTables(XWPFDocument doc) {
        List<XWPFTable> tables = new ArrayList<>();
        
        tables.addAll(doc.getTables());
        
        for (XWPFHeader header : doc.getHeaderList()) {
            tables.addAll(header.getTables());
            
            for (IBodyElement element : header.getBodyElements()) {
                if (element instanceof XWPFSDT) {
                    XWPFSDT xwpfsdt = (XWPFSDT)element;
                    if (xwpfsdt.getContent() instanceof XWPFSDTContent) {
                        XWPFSDTContent content = (XWPFSDTContent)xwpfsdt.getContent();
                        try {
                            Field field = content.getClass().getDeclaredField("bodyElements");
                            field.setAccessible(true);
                            for (ISDTContents c : (List<ISDTContents>)field.get(content)) {
                                if (c instanceof XWPFTable) {
                                    tables.add((XWPFTable)c);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        
        for (XWPFFooter footer : doc.getFooterList()) {
            tables.addAll(footer.getTables());
            
            for (IBodyElement element : footer.getBodyElements()) {
                if (element instanceof XWPFSDT) {
                    XWPFSDT xwpfsdt = (XWPFSDT)element;
                    if (xwpfsdt.getContent() instanceof XWPFSDTContent) {
                        XWPFSDTContent content = (XWPFSDTContent)xwpfsdt.getContent();
                        try {
                            Field field = content.getClass().getDeclaredField("bodyElements");
                            field.setAccessible(true);
                            for (ISDTContents c : (List<ISDTContents>)field.get(content)) {
                                if (c instanceof XWPFTable) {
                                    tables.add((XWPFTable)c);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        
        return tables;
    }
    
    @SuppressWarnings("unchecked")
    private List<XWPFParagraph> getDocumentParagraphs(XWPFDocument doc) {
        List<XWPFParagraph> paragraphs = new ArrayList<>();
        
        paragraphs.addAll(doc.getParagraphs());
        
        for (XWPFHeader header : doc.getHeaderList()) {
            paragraphs.addAll(header.getParagraphs());
            
            for (IBodyElement element : header.getBodyElements()) {
                if (element instanceof XWPFSDT) {
                    XWPFSDT xwpfsdt = (XWPFSDT)element;
                    if (xwpfsdt.getContent() instanceof XWPFSDTContent) {
                        XWPFSDTContent content = (XWPFSDTContent)xwpfsdt.getContent();
                        try {
                            Field field = content.getClass().getDeclaredField("bodyElements");
                            field.setAccessible(true);
                            for (ISDTContents c : (List<ISDTContents>)field.get(content)) {
                                if (c instanceof XWPFParagraph) {
                                    paragraphs.add((XWPFParagraph)c);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        
        for (XWPFFooter footer : doc.getFooterList()) {
            paragraphs.addAll(footer.getParagraphs());
            
            for (IBodyElement element : footer.getBodyElements()) {
                if (element instanceof XWPFSDT) {
                    XWPFSDT xwpfsdt = (XWPFSDT)element;
                    if (xwpfsdt.getContent() instanceof XWPFSDTContent) {
                        XWPFSDTContent content = (XWPFSDTContent)xwpfsdt.getContent();
                        try {
                            Field field = content.getClass().getDeclaredField("bodyElements");
                            field.setAccessible(true);
                            for (ISDTContents c : (List<ISDTContents>)field.get(content)) {
                                if (c instanceof XWPFParagraph) {
                                    paragraphs.add((XWPFParagraph)c);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        
        return paragraphs;
    }
    
    private void replaceBookmarksInParagraph(XWPFParagraph p, Map<String, BookmarkValue> bookmarkMap,
			Map<String, BookmarkHtmlValue> htmlBookmarkMap) {
    	
        Set<CTBookmark> bookmarks = new HashSet<>();
    	for (CTBookmark bookmark : p.getCTP().getBookmarkStartList()) {
			if (bookmarkMap.containsKey(bookmark.getName())) {
				BookmarkValue replacement = bookmarkMap.get(bookmark.getName());
				setParagraphText(p, replacement, bookmark);
				bookmarks.add(bookmark);
			}
			if (htmlBookmarkMap.containsKey(bookmark.getName())) {
				BookmarkHtmlValue replacement = htmlBookmarkMap.get(bookmark.getName());
				setParagraphText(p, replacement);
				// bookmarks.add(bookmark); // is this needed?
			}
    	}
    	for (CTBookmark bookmark : bookmarks) {
    	    p.getCTP().getDomNode().removeChild(bookmark.getDomNode());
    	}
	}

	private void setParagraphText(XWPFParagraph p, BookmarkValue replacement, CTBookmark bookmark) {
	    if (replacement == null) {
	        return;
	    }
	    for (Paragraph par : replacement.getParagraphs()) {
			for (Part part : par.getParts()) {
				XWPFRun run = part.createRun(p);
				
				// font family
				if (part.getFontFamily() == null) {
		            try {
		                if (p.getRuns() != null) {
		                    for (XWPFRun r : p.getRuns()) {
		                        if (r.getFontFamily() != null) {
		                            run.setFontFamily(r.getFontFamily());
		                            break;
		                        }
		                    }
		                }
		            } catch (Exception e) {}
		        }
				
				// font size
				try {
                    if (p.getRuns() != null) {
                        for (XWPFRun r : p.getRuns()) {
                            if (r.getFontSize() > 0) {
                                run.setFontSize(r.getFontSize());
                                break;
                            }
                        }
                    }
                } catch (Exception e) {}
				
				// insert replacement
				Node nextNode = bookmark.getDomNode().getNextSibling();
				while (!(nextNode.getNodeName().contains("bookmarkEnd"))) {
                    p.getCTP().getDomNode().removeChild(nextNode);
                    Node node = bookmark.getDomNode().getNextSibling();
                    if (node == null) {
                        break;
                    } else {
                        nextNode = node;
                    }
                }
				p.getCTP().getDomNode().insertBefore(run.getCTR().getDomNode(), nextNode);
			}
		}
        
	}

	private void setCellText(XWPFTableCell cell, BookmarkHtmlValue replacement) {
		int nPars = cell.getParagraphs().size();
		ParagraphAlignment para = nPars == 0 ? null : cell.getParagraphs().get(0).getAlignment();
        for (int ri = 0; ri < nPars; ri++) {
            cell.removeParagraph(0);
        }
        
        XWPFParagraph paragraph = cell.addParagraph();
        if (para != null) {
            paragraph.setAlignment(para);
        }
        setParagraphText(paragraph, replacement);
	}


	private void setParagraphText(XWPFParagraph paragraph, BookmarkHtmlValue replacement) {
		int n = paragraph.getRuns().size();
        for (int i = 0; i < n; i++) {
            paragraph.removeRun(0);
        }
        //create XmlCursor at this paragraph
        XmlCursor cursor = paragraph.getCTP().newCursor();
        cursor.toEndToken(); //now we are at end of the paragraph
        //there always must be a next start token. Either a p or at least sectPr.
        while(cursor.toNextToken() != org.apache.xmlbeans.XmlCursor.TokenType.START);
        //now we can insert the CTAltChunk here
        String uri = CTAltChunk.type.getName().getNamespaceURI();
        cursor.beginElement("altChunk", uri);
        cursor.toParent();
        CTAltChunk cTAltChunk = (CTAltChunk)cursor.getObject();
        //set the altChunk's Id to reference the given MyXWPFHtmlDocument
        cTAltChunk.setId(replacement.getId());	
	}

	private void replaceBookmarksInTable(XWPFTable tbl, Map<String, BookmarkValue> bookmarkMap, Map<String, BookmarkHtmlValue> htmlBookmarkMap) {
        // Replace in a table
        for (XWPFTableRow row : tbl.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                replaceBookmarksCell(cell, bookmarkMap, htmlBookmarkMap);
            }
        }
    }
    
    private void replaceBookmarksCell(XWPFTableCell cell, Map<String, BookmarkValue> bookmarkMap, Map<String, BookmarkHtmlValue> htmlBookmarkMap) {
        for (XWPFParagraph par : cell.getParagraphs()) {
            CTP ctp = par.getCTP();
            for (CTBookmark bookmark : ctp.getBookmarkStartList()) {
                if (bookmarkMap.containsKey(bookmark.getName())) {
                    BookmarkValue replacement = bookmarkMap.get(bookmark.getName());
                    setCellText(cell, replacement);
                    return;
                }
                if(htmlBookmarkMap.containsKey(bookmark.getName())) {
                	BookmarkHtmlValue replacement = htmlBookmarkMap.get(bookmark.getName());
        			setCellText(cell, replacement);
        			return;
                }
            }
        }
    }

    private void setCellText(XWPFTableCell cell, BookmarkValue value) {
        int nPars = cell.getParagraphs().size();
        ParagraphAlignment para = nPars == 0 ? null : cell.getParagraphs().get(0).getAlignment();
        for (int ri = 0; ri < nPars; ri++) {
            cell.removeParagraph(0);
        }
        
        if (value != null) {
            for (BookmarkValue.Paragraph p : value.getParagraphs()) {
                XWPFParagraph par = cell.addParagraph();
                if (para != null) {
                    par.setAlignment(para);                    
                }
                
                for (BookmarkValue.Paragraph.Part text : p.getParts()) {
                    if (text.getValue() != null) {
                        XWPFRun run = text.createRun(par);
                        par.addRun(run);
                    }
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (doc != null) {
            doc.close();
        }
    }
}
