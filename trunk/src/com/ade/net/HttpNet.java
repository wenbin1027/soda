package com.ade.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:34:14
 */
public class HttpNet {

	private String header;
	private List<IHttpListener> listeners;
	private byte[] postData;
	private HttpResponse response;
	private String url;
	public IHttpListener m_IHttpListener;
	private HttpClient httpclient;
	
	public HttpNet(){
		listeners=new ArrayList();
	}


	/**
	 * 
	 * @param listener
	 */
	public void addListener(IHttpListener listener){
		listeners.add(listener);
	}

	public HttpResponse getResponse(){
		return null;
	}

	public int getStatusCode(){
		return 0;
	}

	/**
	 * 
	 * @param listener
	 */
	public void removeListener(IHttpListener listener){
		listeners.remove(listener);
	}

	/**
	 * 
	 * @param url
	 */
	public void request(HttpUriRequest request){
		HttpClient client=new DefaultHttpClient();
	}

}