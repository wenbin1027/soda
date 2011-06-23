package com.ade.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ade.site.Site;
import com.ade.site.User;

public class NeteaseAccountVerifyParser extends NeteaseBasicParser {
/*	{
	    name: "开放平台"
	    location: ""
	    id: "2229543033866779776"
	    description: ""
	    status: {
	        id: "8619344141274950063"
	        source: "网易微博"
	        text: "这是网易微博开放平台"
	        created_at: "Fri Jul 16 11:52:45 +0800 2010"
	        truncated: false
	        in_reply_to_status_id: null
	        in_reply_to_user_id: null
	        in_reply_to_screen_name: null
	        in_reply_to_user_name: null
	    }
	    screen_name: "openAPI"
	    gender: "0"
	    friends_count: "0"
	    followers_count: "0"
	    statuses_count: "1"
	    created_at: "Fri Jul 16 11:52:23 +0800 2010"
	    favourites_count: "0"
	    profile_image_url: "http://126.fm/15fB1f"
	    url: ""
	    blocking: false
	    following: false
	    followed_by: false
	    verified: false
	}*/

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		JSONObject retUser=new JSONObject(in);
		User myRetUser=site.getLoggedInUser();
		return parseUser(retUser,myRetUser);
	}

}
