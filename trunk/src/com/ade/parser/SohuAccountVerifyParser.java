package com.ade.parser;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.ade.site.Site;
import com.ade.site.User;

public class SohuAccountVerifyParser extends Parser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		/*{"id":"88612924",
			"screen_name":"wmdev",
			"name":"",
			"location":"北京市,海淀区",
			"description":"",
			"url":"",
			"profile_image_url":"http://www.chinaren.com/upload/ticon/avt_48.jpg",
			"protected":true,
			"followers_count":6,
			"profile_background_color":"",
			"profile_text_color":"",
			"profile_link_color":"",
			"profile_sidebar_fill_color":"",
			"profile_sidebar_border_color":"",
			"friends_count":8,
			"created_at":"Sat Apr 23 09:34:53 +0800 2011",
			"favourites_count":0,
			"utc_offset":"",
			"time_zone":"",
			"profile_background_image_url":"",
			"notifications":"",
			"geo_enabled":false,
			"statuses_count":2,
			"following":true,
			"verified":false,
			"lang":"zh_cn",
			"contributors_enabled":false
		}*/

		User blogUser=site.getLoggedInUser();
		JSONObject user=new JSONObject(in);;
		blogUser.setID(user.getLong("id"));
		blogUser.setScreenName(user.getString("screen_name"));
		blogUser.setName(user.getString("name"));
		blogUser.setLocation(user.getString("location"));
		blogUser.setDescription(user.getString("description"));
		blogUser.setUrl(user.getString("url"));
		blogUser.setProfileImageUrl(user.getString("profile_image_url"));
		blogUser.setFollowersCount(user.getLong("followers_count"));
		blogUser.setFriendsCount(user.getLong("friends_count"));
		blogUser.setCreatedAt(new Date(user.getString("created_at")));
		blogUser.setFavouritesCount(user.getLong("favourites_count"));
		blogUser.setBlogsCount(user.getLong("statuses_count"));
		blogUser.setFollowing(user.getBoolean("following"));
		blogUser.setVerified(user.getBoolean("verified"));
		return true;
	}

}
