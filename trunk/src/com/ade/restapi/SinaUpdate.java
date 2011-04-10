package com.ade.restapi;

import org.apache.http.Header;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:29:11
 */
public class SinaUpdate extends UpdateInterface {

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected String getUrl(String text, Site site){
		return "";
	}

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected Header[] getHeader(String text, Site site){
		return null;
	}

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected byte[] getPostData(String text, Site site){
		return null;
	}

}