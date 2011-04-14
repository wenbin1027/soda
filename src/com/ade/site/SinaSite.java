package com.ade.site;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:50
 */
public class SinaSite extends Site {

	public SinaSite(){
		name="新浪微博";
		rootUrl="http://api.t.sina.com.cn";
		appKey="3393006127";  
		appSecret="70768c222a4613ed7f930bae3dee2e57";
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	protected void onConstruct(){

	}

	@Override
	public void onBeginRequest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String errorMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponsed(StatusLine statusLine, Header[] headers,
			HttpEntity entity) {
		// TODO Auto-generated method stub
		
	}

}