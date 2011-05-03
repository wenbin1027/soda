package com.ade.parser;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.ade.site.Site;
import com.ade.site.User;

public class SinaAccountVerifyParser extends Parser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		/*{"id":1425507814,
			"screen_name":"移动云_曹文斌",
			"name":"移动云_曹文斌",
			"province":"11",
			"city":"8",
			"location":"北京 海淀区",
			"description":"以移动互联网为研究方向，长于项目管理与团队建设",
			"url":"http://blog.csdn.net/caowenbin",
			"profile_image_url":"http://tp3.sinaimg.cn/1425507814/50/5596891562/1",
			"domain":"wenbin1027",
			"gender":"m",
			"followers_count":198,
			"friends_count":177,
			"statuses_count":374,
			"favourites_count":0,
			"created_at":"Wed Jul 07 00:00:00 +0800 2010",
			"following":false,
			"allow_all_act_msg":false,
			"geo_enabled":true,
			"verified":false,
			"status":{...}
		}*/
		JSONObject retUser=new JSONObject(in);
		User myRetUser=site.getLoggedInUser();
		myRetUser.setID(retUser.getLong("id"));
		myRetUser.setScreenName(retUser.getString("screen_name"));
		myRetUser.setName(retUser.getString("name"));
		myRetUser.setLocation(retUser.getString("location"));
		myRetUser.setDescription(retUser.getString("description"));
		myRetUser.setUrl(retUser.getString("url"));
		myRetUser.setProfileImageUrl(retUser.getString("profile_image_url"));
		myRetUser.setGender(retUser.getString("gender"));
		myRetUser.setFollowersCount(retUser.getLong("followers_count"));
		myRetUser.setFriendsCount(retUser.getLong("friends_count"));
		myRetUser.setBlogsCount(retUser.getLong("statuses_count"));
		myRetUser.setFavouritesCount(retUser.getLong("favourites_count"));
		myRetUser.setCreatedAt(new Date(retUser.getString("created_at")));
		myRetUser.setFollowing(retUser.getBoolean("following"));
		myRetUser.setVerified(retUser.getBoolean("verified"));
		return true;
	}

}
