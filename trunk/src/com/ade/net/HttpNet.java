package com.ade.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.ade.parser.Parser;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:34:14
 */
public class HttpNet {

	protected IHttpListener listener;
	protected HttpClient client;
	protected Parser parser;
	protected HttpUriRequest request;

	protected ResponseHandler<String> responseHandler=new ResponseHandler<String>(){
		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			//statusCode=response.getStatusLine().getStatusCode();
			notifyResponse(response.getStatusLine(),response.getAllHeaders(),response.getEntity());
			return null;
		}
	};

	public HttpNet(){
		
	}
	
	public HttpNet(IHttpListener listener){
		this.listener=listener;
	}
	/**
	 * 
	 * @param listener
	 */
	public void setListener(IHttpListener listener){
		this.listener=listener;
	}

	/**
	 * 
	 * @param url
	 */
	public void request(HttpUriRequest request){
		request(request,null);
	}
	
	public void request(HttpUriRequest request,Parser parser){
		this.parser=parser;
		this.request=request;
		client=new DefaultHttpClient();
		new Thread(new Runnable(){
			@Override
			public void run() {
				HttpNet.this.request.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
						false);  //消除握手
				try {
					client.execute(HttpNet.this.request, responseHandler);
					notifyBegin();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					notifyError("Client Protocol ERROR!");
				} catch (IOException e) {
					e.printStackTrace();
					notifyError("Internet access ERROR!");
				}				
			}
		}).start();

	}
	
	public void cancel(){
		if (request!=null){
			if (!request.isAborted()){
				request.abort();
			}
		}
	}
	
	protected void notifyBegin(){
		if (listener!=null)
			listener.onBeginRequest();
	}
	
	protected void notifyError(String errorMessage){
		if (listener!=null)
			listener.onError(errorMessage,parser);
	}
	
	protected void notifyResponse(StatusLine statusLine,Header[] headers,HttpEntity entity){
		if (listener!=null)
			listener.onResponsed(statusLine,headers,entity,parser);
	}
}