package com.ade.restapi;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;

import com.ade.parser.Parser;
import com.ade.site.Site;
import com.ade.util.OAuthUtil;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:09
 */
public abstract class UploadInterface extends ApiInterface {

	public UploadInterface(Parser parser) {
		super(parser);
	}

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
	 * @throws IOException 
	 */
	protected abstract byte[] getPostData(String fileName, String text, Site site) throws IOException;

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
	public boolean isValid(String fileName, String text){
		if (fileName==null)
			return false;
		int length=fileName.trim().length();
		if (length<=0 || length>300 || text==null || text.length()<=0 || text.length()>140)
			return false;
		return true;
	}

	public HttpUriRequest getRequest(String fileName, String text, Site site) throws IOException{
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
			try {
				entity=new ByteArrayEntity(getPostData(fileName,textEncode,site));
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
			request.setEntity(entity);
		}
		return request;
	}
}