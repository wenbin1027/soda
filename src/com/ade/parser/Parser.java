package com.ade.parser;

import java.io.InputStream;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:34:38
 */
public abstract class Parser {

	public Parser(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param stream
	 */
	public int getErrorCode(InputStream stream){
		return 0;
	}

	/**
	 * 
	 * @param stream
	 * @param site
	 */
	protected abstract boolean onParse(InputStream stream, Site site);

	/**
	 * 
	 * @param stream
	 * @param site
	 */
	public boolean parse(InputStream stream, Site site){
		return false;
	}

}