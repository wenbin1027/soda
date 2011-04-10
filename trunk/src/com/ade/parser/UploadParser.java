package com.ade.parser;

import java.io.InputStream;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:34:39
 */
public class UploadParser extends Parser {

	public UploadParser(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param stream
	 * @param site
	 */
	protected boolean onParse(InputStream stream, Site site){
		return false;
	}

}