package com.ade.restapi;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.ade.parser.Parser;
import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:15
 */
public class SohuFriendsTimeline extends FriendsTimelineInterface {
	private static final String PATH="/statuses/friends_timeline.json";
	
	public SohuFriendsTimeline(Parser parser) {
		super(parser);
	}
	protected String getUrl(int count, int page, Site site){
		StringBuilder sb=new StringBuilder(site.getRootUrl());
		sb.append(PATH);
		if (count>=0 || page>=0){
			sb.append('?');
		}
		if (count>=0){
			sb.append("count=");
			sb.append(count);			
		}
		if (count>=0 && page>=0)
			sb.append('&');
		
		if (page>=0){
			sb.append("page=");
			sb.append(page);
		}
		
		return sb.toString();
	}

	@Override
	protected List<NameValuePair> getParams(int count, int page, Site site) {
		if (count>=0 || page>=0){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			if (count>=0)
				params.add(new BasicNameValuePair("count",""+count));
			if (page>=0)
				params.add(new BasicNameValuePair("page",""+page));
			return params;
		}
		return null;
	}
}