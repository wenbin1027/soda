package com.ade.restapi;

import java.net.URLEncoder;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import com.ade.site.Site;
import com.ade.util.OAuthUtil;

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
		String textEncode=URLEncoder.encode(text);
		String fileNameEncode=URLEncoder.encode(fileName);
		HttpPost request=new HttpPost(getUrl(fileNameEncode,textEncode,site));
		Header[] headers=getHeader(fileNameEncode,textEncode,site);
		if (headers!=null){
			for(int i=0;i<headers.length;i++){
				request.addHeader(headers[i]);
			}
		}
		List<NameValuePair> data=getParams(fileNameEncode,textEncode,site);
		if (data!=null){
			OAuthUtil.signRequest(getUrl(fileNameEncode,textEncode,site), request, data, 
					site.getAppKey(), site.getAppSecret(), 
					site.getAccessKey(), site.getAccessSecret());
			ByteArrayEntity entity;
			entity=new ByteArrayEntity(getPostData(fileName,textEncode,site));
			request.setEntity(entity);
		}
		return request;
	}
}