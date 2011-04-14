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
 * @created 10-����-2011 ���� 08:29:09
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

	protected abstract List<NameValuePair> getParams(String fileName,String text,Site site);
	
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
			List<NameValuePair> data=getParams(fileName,text,site);
			if (data!=null){
				StringEntity entity;
				try {
					entity = new UrlEncodedFormEntity(data);
					request.setEntity(entity);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
//			byte[]data=getPostData(fileName,text,site);
//			if (data!=null){
//				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(null); //TODO
//				request.setEntity(entity);
//			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return request;
	}
}