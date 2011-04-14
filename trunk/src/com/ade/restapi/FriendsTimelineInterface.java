package com.ade.restapi;


import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import com.ade.site.Site;
import com.ade.util.OAuthUtil;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:04
 */
public abstract class FriendsTimelineInterface {

	public HttpUriRequest getRequest(int count, int page, Site site){
		HttpGet request=null;
		String url=getUrl(count,page,site);
		request=new HttpGet(url);
		List<NameValuePair> data=getParams(count,page,site);
		OAuthUtil.signRequest(url, request, data, 
				site.getAppKey(),site.getAppSecret(),
				site.getAccessKey(), site.getAccessSecret());
		
		return request;
	}
	
	protected abstract List<NameValuePair> getParams(int count, int page,Site site);

	protected abstract String getUrl(int count, int page, Site site);
}
