package com.abelium.inatrace.tools.documents;

import org.apache.poi.ooxml.POIXMLRelation;

public final class BookmarkHtmlRelation extends POIXMLRelation {
	public BookmarkHtmlRelation() {
		super("text/html", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/aFChunk", "/word/htmlDoc#.html");
	}
}
