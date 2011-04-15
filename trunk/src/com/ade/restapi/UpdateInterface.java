package com.ade.restapi;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:07
 */
public abstract class UpdateInterface {

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected abstract Header[] getHeader(String text, Site site);


	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected abstract String getUrl(String text, Site site);

	protected abstract List<NameValuePair> getParams(String text,Site site);
	/**
	 * 
	 * @param text
	 */
	static public boolean isValid(String text){
		if (text==null)
			return false;
		int length=text.trim().length();
		if (length<1 || length>140)
			return false;
		return true;
	}
	
	public HttpUriRequest getRequest(String text, Site site){
		HttpPost request=new HttpPost(getUrl(text,site));
		Header[] headers=getHeader(text,site);
		if (headers!=null){
			for(int i=0;i<headers.length;i++){
				request.addHeader(headers[i]);
			}
		}
		List<NameValuePair> data=getParams(text,site);
		if (data!=null){
			StringEntity entity;
			try {
				entity = new UrlEncodedFormEntity(data);
				request.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return request;
	}

}