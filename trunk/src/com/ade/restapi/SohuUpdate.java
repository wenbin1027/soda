package com.ade.restapi;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:16
 */
public class SohuUpdate extends UpdateInterface {


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

	@Override
	protected List<NameValuePair> getParams(String text, Site site) {
		return null;
	}


}