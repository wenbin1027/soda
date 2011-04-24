package com.ade.parser;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public class SohuFriendsTimelineParser extends Parser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		/*
		[{"created_at":"Fri Mar 12 22:21:42 +0800 2010",
			"id":"442779",
			"text":"win7 自带的截图软件有快捷键吗?",
			"source":"web",
			"favorited":"0",
			"truncated":"false",
			"in_reply_to_status_id":"",
			"in_reply_to_user_id":"",
			"in_reply_to_screen_name":"",
			"small_pic":"",
			"middle_pic":"",
			"original_pic":"",
			"user":{
			    "id":"1093",
			    "screen_name":"Hongtium",
			    "name":"",
			    "location":"",
			    "description":"生活在封建统治阶级下",
			    "url":"",
			    "profile_image_url":"http://up2.upload.chinaren.com/mblog/icon/68/b1/m_12670300466279.JPG",
			    "protected":false,
			    "followers_count":238,
			    "profile_background_color":"",
			    "profile_text_color":"",
			    "profile_link_color":"",
			    "profile_sidebar_fill_color":"",
			    "profile_sidebar_border_color":"",
			    "friends_count":63,
			    "created_at":"Fri Dec 04 16:11:56 +0800 2009",
			    "favourites_count":0,
			    "utc_offset":"",
			    "time_zone":"",
			    "profile_background_image_url":"",
			    "notifications":"",
			    "geo_enabled":false,
			    "statuses_count":870,
			    "following":true,
			    "verified":false,
			    "lang":"GBK",
			    "contributors_enabled":false}}
			…]
		*/

		JSONArray blogs=new JSONArray(in);
		if (blogs!=null){
			for(int i=0;i<blogs.length();i++){
				JSONObject blog=blogs.getJSONObject(i);
				if (blog!=null){
					Blog newBlog=new Blog();
					newBlog.setCreatedAt(new Date(blog.getString("created_at")));
					newBlog.setID(blog.getLong("id"));
					newBlog.setText(blog.getString("text"));
					newBlog.setInReplyToStatusID(blog.optLong("in_reply_to_status_id"));
					newBlog.setInReplyToUserID(blog.optLong("in_reply_to_user_id"));
					newBlog.setInReplyToScreenName(blog.optString("in_reply_to_screen_name"));
					newBlog.setInReplyToStatusText(blog.optString("in_reply_to_status_text"));
					
					//User数据部分
					User blogUser=new User();
					JSONObject user=blog.getJSONObject("user");
					blogUser.setID(user.getLong("id"));
					blogUser.setScreenName(user.getString("screen_name"));
					blogUser.setName(user.getString("name"));
					blogUser.setLocation(user.getString("location"));
					blogUser.setDescription(user.getString("description"));
					blogUser.setUrl(user.getString("url"));
					blogUser.setProfileImageUrl(user.getString("profile_image_url"));
					blogUser.setFollowersCount(user.getLong("followers_count"));
					blogUser.setCreatedAt(new Date(user.getString("created_at")));
					blogUser.setVerified(user.getBoolean("verified"));
					newBlog.setUser(blogUser);
					
					site.addBlog(newBlog);
				}
			}
		}
		
		return true;
	}

}
