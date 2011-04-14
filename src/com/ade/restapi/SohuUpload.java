package com.ade.restapi;

import java.io.*;

import java.util.logging.Filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Path.FillType;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:18
 */
public class SohuUpload extends UploadInterface {
	//The Common string of the multipart/form-data encoding format
	private static final String mf1="Content-Type: multipart/form-data; boundary=---------------------------";
	private static final String mf2="\r\nContent-Disposition: form-data; name=\"pic\"; filename=";	
	private static final String mf3="\r\nContent-Type: image/jpeg\r\n\r\n";
	protected String rn="\r\n";
	protected String L="---------------------------";
	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected String getUrl(String fileName, String text, Site site){
		return "http://api.t.sohu.com/statuses/upload.json";
	}

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected Header[] getHeader(String fileName, String text, Site site){
		return null;
	}

	/**
	 * 
	 * @param fileName
	 * @param text
	 * @param site
	 */
	protected byte[] getPostData(String fileName, String text, Site site){
		//According to the data file names read in, then follow the multipart / form-data encoding data structure organized into upstream
		String Q="\"";
		String Boundary=site.getBoundary();
		StringBuilder dat=new StringBuilder(mf1);
		dat.append(Boundary);
		dat.append(rn);
		dat.append(L);
		dat.append(Boundary);
		dat.append(mf2);
		dat.append(Q);
		dat.append(fileName);
		dat.append(Q);
		dat.append(mf3);
		File file= new File(fileName);
		InputStream in = null;
		//To read the contents of the file in bytes
		try {
			in = new FileInputStream(file);
			int tempbyte;
			while((tempbyte=in.read()) != -1){
				dat.append(tempbyte);
				}
			in.close();
			} catch (IOException e) {
		      e.printStackTrace();
		      }
		dat.append(rn);
		dat.append(L);
		dat.append(Boundary);
		return dat.toString().getBytes();
	}

	@Override
	protected List<NameValuePair> getParams(String fileName, String text,
			Site site) {
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fileName",fileName));
		params.add(new BasicNameValuePair("text",text));
		return params;
	}

}