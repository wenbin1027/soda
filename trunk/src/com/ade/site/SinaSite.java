package com.ade.site;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

import com.ade.parser.Parser;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:50
 */
public class SinaSite extends Site {

	public SinaSite(){
		name="新浪微博";
		rootUrl="http://api.t.sina.com.cn";
		appKey="2321851444";  
		appSecret="786fd8cb4d3f237599b5d587b6f6c0e9";
		oauthRequestUrl="/oauth/request_token";
		oauthUrl="/oauth/authorize";
		oauthAccessUrl="/oauth/access_token";
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	protected void onConstruct(){

	}

	@Override
	public void onError(String errorMessage,Parser parser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponsed(StatusLine statusLine, Header[] headers,
			HttpEntity entity,Parser parser) {
		// TODO Auto-generated method stub
		
	}

}