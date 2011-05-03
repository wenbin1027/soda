package com.ade.restapi;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import com.ade.parser.Parser;
import com.ade.site.Site;
import com.ade.util.OAuthUtil;

public abstract class AccountVerifyInterface extends ApiInterface {

	public AccountVerifyInterface(Parser parser) {
		super(parser);
	}
	
	protected abstract String getUrl(Site site);
	
	public HttpUriRequest getRequest(Site site){
		HttpGet request=null;
		String url=getUrl(site);

		url=OAuthUtil.signGetRequest(url, null,
				site.getAppKey(),site.getAppSecret(),
				site.getAccessKey(), site.getAccessSecret());
		
		request=new HttpGet(url);
		
		return request;
	}
}
