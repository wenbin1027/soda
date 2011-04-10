package com.ade.restapi;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:29:10
 */
public class SinaFriendsTimeline extends FriendsTimelineInterface {

	private static final String PATH="/statuses/friends_timeline.json";

	protected String getUrl(int count, int page, Site site){
		StringBuilder sb=new StringBuilder(site.getRootUrl());
		sb.append(PATH);
		if (count>0 && page>0){
			sb.append('?');
		}
		if (count>0){
			sb.append("&count=");
			sb.append(count);			
		}
		if (page>0){
			sb.append("&page=");
			sb.append(page);
		}
		
		return sb.toString();
	}

}