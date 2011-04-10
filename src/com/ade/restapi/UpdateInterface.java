package com.ade.restapi;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:29:07
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
	protected abstract byte[] getPostData(String text, Site site);

	/**
	 * 
	 * @param text
	 * @param site
	 */
	protected abstract String getUrl(String text, Site site);

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
		HttpPost request=null;
		try {
			request=new HttpPost(new URI(getUrl(text,site)));
			Header[] headers=getHeader(text,site);
			if (headers!=null){
				for(int i=0;i<headers.length;i++){
					request.addHeader(headers[i]);
				}
			}
			byte[]data=getPostData(text,site);
			if (data!=null){
				ByteArrayEntity entity=new ByteArrayEntity(data);
				request.setEntity(entity);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return request;
	}

}