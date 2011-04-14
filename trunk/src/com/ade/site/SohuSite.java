package com.ade.site;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:53
 */
public class SohuSite extends Site {

	public SohuSite(){
		name="搜狐微博";
		rootUrl="http://api.t.sohu.com";
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