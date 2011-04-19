package com.ade.site;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

import com.ade.parser.Parser;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:53
 */
public class SohuSite extends Site {

	protected void onConstruct(){
		name="搜狐微博";
		rootUrl="http://api.t.sohu.com";
		appKey="bHi9G9CcHCeXBMhWReow";  
		appSecret="ITDdmLZCeALHO847#DWlRn2Vl-z2lcU%U!RO4DIo";
		oauthRequestUrl="/oauth/request_token";
		oauthUrl="/oauth/authorize";
		oauthAccessUrl="/oauth/access_token";
	}

	@Override
	public void onError(String errorMessage,Parser parser) {
		notifyError(errorMessage);
	}

	@Override
	public void onResponsed(StatusLine statusLine, Header[] headers,
			HttpEntity entity,Parser parser) {
		
		if (statusLine.getStatusCode()==401){  //Unauthorized
			onError("用户未授权",parser);
			return;
		}
		isLoggedIn=true;
		
		notifyResponse();
	}

}