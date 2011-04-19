package com.ade.restapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:11
 */
public class SinaUpdate extends UpdateInterface {

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected String getUrl(String text, Site site){
		return site.getRootUrl()+"/statuses/update.json";
	}

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected Header[] getHeader(String text, Site site){
		Header[] headers=new Header[1];
		headers[0]=new BasicHeader("Content-Type","application/x-www-form-urlencoded");
		return headers;
	}

	@Override
	protected List<NameValuePair> getParams(String text, Site site) {
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("status",text));
		return params;
	}

}