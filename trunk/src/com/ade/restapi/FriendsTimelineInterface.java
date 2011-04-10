package com.ade.restapi;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:29:04
 */
public abstract class FriendsTimelineInterface {

	public HttpUriRequest getRequest(int count, int page, Site site){
		HttpGet request=null;
		try {
			request=new HttpGet(new URI(getUrl(count,page,site)));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return request;
	}
	
	protected abstract String getUrl(int count, int page, Site site);
}
