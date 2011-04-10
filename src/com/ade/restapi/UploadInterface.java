package com.ade.restapi;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:29:09
 */
public abstract class UploadInterface {

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected abstract Header[] getHeader(String fileName, String text, Site site);

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected abstract byte[] getPostData(String fileName, String text, Site site);

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected abstract String getUrl(String fileName, String text, Site site);

	/**
	 * 
	 * @param fileName
	 * @param text
	 */
	static public boolean isValid(String fileName, String text){
		if (fileName==null)
			return false;
		int length=fileName.trim().length();
		if (length<1 || length>300)
			return false;
		return true;
	}

	public HttpUriRequest getRequest(String fileName, String text, Site site){
		HttpPost request=null;
		try {
			request=new HttpPost(new URI(getUrl(fileName,text,site)));
			Header[] headers=getHeader(fileName,text,site);
			if (headers!=null){
				for(int i=0;i<headers.length;i++){
					request.addHeader(headers[i]);
				}
			}
			byte[]data=getPostData(fileName,text,site);
			if (data!=null){
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(null); //TODO
				request.setEntity(entity);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return request;
	}
}