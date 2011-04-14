package com.ade.restapi;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:12
 */
public class SinaUpload extends UploadInterface {

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

	@Override
	protected List<NameValuePair> getParams(String fileName, String text,
			Site site) {
		return null;
	}

}