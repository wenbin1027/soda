package com.ade.restapi;

import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import com.ade.parser.Parser;
import com.ade.site.Site;
import com.ade.util.OAuthUtil;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:04
 */
public abstract class FriendsTimelineInterface extends ApiInterface{

	public FriendsTimelineInterface(Parser parser) {
		super(parser);
	}

	public HttpUriRequest getRequest(int count, int page, Site site){
		HttpGet request=null;
		String url=getUrl(count,page,site);

		List<NameValuePair> data=getParams(count,page,site);
		url=OAuthUtil.signGetRequest(url, data,
				site.getAppKey(),site.getAppSecret(),
				site.getAccessKey(), site.getAccessSecret());
		
		request=new HttpGet(url);
		
		return request;
	}
	
	protected abstract List<NameValuePair> getParams(int count, int page,Site site);

	protected abstract String getUrl(int count, int page, Site site);
}
