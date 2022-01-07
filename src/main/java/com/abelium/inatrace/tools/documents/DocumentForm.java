package com.abelium.inatrace.tools.documents;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

public interface DocumentForm {
    
    default String getTemplatePath() {
    	return null;
    }
    
    default byte[] getByteArray() {
    	return null;
    }
    
    default InputStream getInputStream(String root) throws FileNotFoundException {
    	if(getTemplatePath() != null) {
    		String path = Paths.get(root, getTemplatePath()).toString();
            return new FileInputStream(path);
    	}
    	if(getByteArray() != null) {
    		return new ByteArrayInputStream( getByteArray() );
    	}
    	throw new RuntimeException("Either getTemplatePath or getByteArray must be implemented");
    }
}
