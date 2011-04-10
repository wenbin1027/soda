package com.ade.restapi;

import org.apache.http.Header;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:29:18
 */
public class SohuUpload extends UploadInterface {

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected String getUrl(String fileName, String text, Site site){
		return "";
	}

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected Header[] getHeader(String fileName, String text, Site site){
		return null;
	}

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected byte[] getPostData(String fileName, String text, Site site){
		return null;
	}

}