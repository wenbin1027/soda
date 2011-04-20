package com.ade.restapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.ade.parser.Parser;
import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:12
 */
public class SinaUpload extends UploadInterface {

	public SinaUpload(Parser parser) {
		super(parser);
	}

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected String getUrl(String fileName, String text, Site site){
		return site.getRootUrl()+"/statuses/upload.json";
	}

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected Header[] getHeader(String fileName, String text, Site site){
		Header[] headers=new Header[1];
		headers[0]=new BasicHeader("Content-Type","multipart/form-data; boundary="+site.getBoundary());
		return headers;
	}

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected byte[] getPostData(String fileName, String text, Site site) throws IOException{
		String boundary=site.getBoundary();
		StringBuilder front=new StringBuilder("--"+boundary);
		front.append("\r\nContent-Disposition: form-data; name=\"status\"\r\n\r\n");
		front.append(text);
		front.append("\r\n--"+boundary);
		front.append("\r\nContent-Disposition: form-data; name=\"pic\"; filename=\"temp\"");
		front.append("\r\nContent-type: application/octet-stream\r\n\r\n");
		File imageFile = new File(fileName);
		byte[] fileBuffer = new byte[(int)imageFile.length()];
		try {
			FileInputStream is = new FileInputStream(fileName);
			is.read(fileBuffer);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
	    }
		StringBuilder back=new StringBuilder("\r\n--"+boundary+"--"+"\r\n");
		byte[] PostData=new byte[(int)(front.toString().getBytes().length+fileBuffer.length+back.toString().getBytes().length)];
		System.arraycopy(front.toString().getBytes(),0,PostData,0,front.toString().getBytes().length);
		System.arraycopy(fileBuffer,0,PostData,(int)(front.toString().getBytes().length),fileBuffer.length);
		System.arraycopy(back.toString().getBytes(),0,PostData,(int)(front.toString().getBytes().length+fileBuffer.length),back.toString().getBytes().length);
		return PostData;
	}

	@Override
	protected List<NameValuePair> getParams(String fileName, String text,
			Site site) {
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("status",text));
		return params;
	}

}