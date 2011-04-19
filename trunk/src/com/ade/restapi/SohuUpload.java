package com.ade.restapi;

import java.io.*;
//import java.util.logging.Filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

//import android.graphics.Path.FillType;

import com.ade.site.Site;

import dalvik.system.TemporaryDirectory;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:18
 */
public class SohuUpload extends UploadInterface {
	//The Common string of the multipart/form-data encoding format
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
	protected byte[] getPostData(String fileName, String text, Site site){
		String Boundary=site.getBoundary();
		StringBuilder Front=new StringBuilder("--"+site.getBoundary());
		Front.append("\r\nContent-Disposition: form-data; name=\"status\"\r\n\r\n");
		Front.append(text);
		Front.append("\r\n--"+site.getBoundary());
		Front.append("\r\nContent-Disposition: form-data; name=\"pic\"; filename=\"temp\"");
		Front.append("\r\nContent-type: application/octet-stream\r\n\r\n");
		File Imagefile = new File(fileName);
		byte[] filebuffer = new byte[(int)Imagefile.length()];
		try {
			FileInputStream is = new FileInputStream(fileName);
			is.read(filebuffer);
			is.close();
			} catch (IOException e) {
			e.printStackTrace();//待UI完成后再次修改，于UI中显示文件读入错误
		      }
		StringBuilder Back=new StringBuilder("\r\n--"+site.getBoundary()+"--"+"\r\n");
		byte[] PostData=new byte[(int)(Front.toString().getBytes().length+filebuffer.length+Back.toString().getBytes().length)];
		System.arraycopy(Front.toString().getBytes(),0,PostData,0,Front.toString().getBytes().length);
		System.arraycopy(filebuffer,0,PostData,(int)(Front.toString().getBytes().length),filebuffer.length);
		System.arraycopy(Back.toString().getBytes(),0,PostData,(int)(Front.toString().getBytes().length+filebuffer.length),Back.toString().getBytes().length);
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