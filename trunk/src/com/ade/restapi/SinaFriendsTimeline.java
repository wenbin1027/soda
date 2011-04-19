package com.ade.restapi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import com.ade.parser.Parser;
import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:29:10
 */
public class SinaFriendsTimeline extends FriendsTimelineInterface {
	private static final String PATH="/statuses/friends_timeline.json";

	public SinaFriendsTimeline(Parser parser) {
		super(parser);
	}
	
	protected String getUrl(int count, int page, Site site){
		return new String(site.getRootUrl()+PATH);
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