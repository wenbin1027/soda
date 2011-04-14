package com.ade.restapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:16
 */
public class SohuUpdate extends UpdateInterface {
	//The Common string of the multipart/form-data encoding format
	private static final String mf1="Content-Type: multipart/form-data; boundary=---------------------------";
	private static final String mf2="\r\nContent-Disposition: form-data; name=\"status\"";	
	protected String rn="\r\n";
	protected String L="---------------------------";
	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected String getUrl(String text, Site site){
		return "http://api.t.sohu.com/statuses/upload.json";
	}

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected Header[] getHeader(String text, Site site){
		return null;
	}
	protected byte[] getPostData(String text, Site site){
		//According to multipart / form-data encoding data structure organized into upstream
		String Boundary=site.getBoundary();
		StringBuilder dat=new StringBuilder(mf1);
		dat.append(Boundary);
		dat.append(rn);
		dat.append(L);
		dat.append(Boundary);
		dat.append(mf2);
		dat.append(rn);
		dat.append(text);
		dat.append(rn);
		dat.append(L);
		dat.append(Boundary);
		return dat.toString().getBytes();
	}
	@Override
	protected List<NameValuePair> getParams(String text, Site site) {
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("text",text));
		return params;
	}

}