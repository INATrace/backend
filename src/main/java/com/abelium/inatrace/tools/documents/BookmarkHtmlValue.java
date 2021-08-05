package com.abelium.inatrace.tools.documents;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

// https://stackoverflow.com/questions/53863126/how-to-replace-texttag-with-html-in-docx-using-apache-poi
// https://stackoverflow.com/questions/38429878/how-to-convert-2007-docx-files-having-altchunk-content-to-pdf-using-java
// https://stackoverflow.com/questions/54684056/convert-altchunks-in-docx-using-docx4j
// https://stackoverflow.com/questions/53564028/how-to-add-an-altchunk-element-to-a-xwpfdocument-using-apache-poi
public class BookmarkHtmlValue extends POIXMLDocumentPart {

	  private String html;
	  private String id;

	  public BookmarkHtmlValue(PackagePart part, String id) throws Exception {
		  super(part);
		  this.html = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		  		  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "+
				  "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
				  "<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
				  "<head>"+
				  "<title>Title of document</title>"+
				  "<style type=\"text/css\">" +
				  "<!--" +
				  "p { font-family: \"Archivo Narrow\" !important; margin-bottom: 0; line-height: 100%; }" +
				  "-->" +
				  "</style>" +
				  "</head>"+
				  "<body></body>"+
				  "</html>";
		  this.id = id;
	  }

	  public String getId() {
		  return id;
	  }

	  public String getHtml() {
		  return html;
	  }

	  public void setHtml(String html) {
		  this.html = html;
	  }
	  
	  public void addHtmlToBody(String html) {
		  setHtml(this.html.replace("<body></body>", "<body style=\"font-family:\'Archivo Narrow\' !important;\">" + html + "</body>"));
	  }
	  
	  public static BookmarkHtmlValue createHtmlDocument(XWPFDocument doc, int id, String html) throws Exception {
		  BookmarkHtmlValue htmlVal = createHtmlDocument(doc, "htmlDoc" + id);
		  htmlVal.addHtmlToBody(html);
		  return htmlVal;
	  }
	  
	  private static BookmarkHtmlValue createHtmlDocument(XWPFDocument doc, String id) throws Exception {
		  OPCPackage oPCPackage = doc.getPackage();
		  PackagePartName partName = PackagingURIHelper.createPartName("/word/" + id + ".xhtml");
		  PackagePart part = oPCPackage.createPart(partName, "application/xhtml+xml");
		  BookmarkHtmlValue document = new BookmarkHtmlValue(part, id);
		  doc.addRelation(document.getId(), new BookmarkHtmlRelation(), document);
		  return document;
	  }

	  @Override
	  protected void commit() throws IOException {
		  PackagePart part = getPackagePart();
		  OutputStream out = part.getOutputStream();
		  Writer writer = new OutputStreamWriter(out, "UTF-8");
		  writer.write(html);
		  writer.close();
		  out.close();
	  }
 }
