package com.ade.parser;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public class SohuUploadParser extends Parser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		// TODO Auto-generated method stub
		/*
		[{"created_at":"Mon Apr 19 21:35:33 +0800 2010",
		"id":"7496193",
		"text":"今天，他们总统乐着说，这样的风景多么难得啊？赶紧飞过来旅游吧！真是乐观的人",
		"source":"web",
		"favorited":"0",
		"truncated":"false",
		"in_reply_to_status_id":"",
		"in_reply_to_user_id":"",
		"in_reply_to_screen_name":"",
		"in_reply_to_status_text":"",
		"small_pic":"http://1822.img.pp.sohu.com.cn/images/mblog/2010/4/19/21/3/128c8e73071g213_1.jpg",
		"middle_pic":"http://1822.img.pp.sohu.com.cn/images/mblog/2010/4/19/21/3/128c8e73071g213_0.jpg",
		"original_pic":"http://1822.img.pp.sohu.com.cn/images/mblog/2010/4/19/21/3/128c8e73071g213_0.jpg",
		"user":{
		    "id":"997604",
		    "screen_name":"方礼勇",
		    "name":"",
		    "location":"",
		    "description":"方礼勇,中国互联网一线的实践者和思考者,北大EMBA。",
		    "url":"",
		    "profile_image_url":"http://1821.img.pp.sohu.com.cn/images/blog/2009/2/7/21/9/11ffab2fdc5g214.jpg",
		    "protected":false,
		    "followers_count":188,
		    "profile_background_color":"",
		    "profile_text_color":"",
		    "profile_link_color":"",
		    "profile_sidebar_fill_color":"",
		    "profile_sidebar_border_color":"",
		    "friends_count":879,
		    "created_at":"Tue Mar 16 19:16:16 +0800 2010",
		    "favourites_count":0,
		    utc_offset":"",
		    "time_zone":"",
		    "profile_background_image_url":"",
		    "notifications":"",
		    "geo_enabled":false,
		    "statuses_count":233,
		    "following":false,
		    "verified":true,
		    "lang":"zh_cn",
		    "contributors_enabled":false},}}
			…]
		 */
		JSONObject blog=new JSONObject(in);
		if (blog!=null){
			Blog newBlog=new Blog();
			newBlog.setCreatedAt(new Date(blog.getString("created_at")));
			newBlog.setID(blog.getLong("id"));
			newBlog.setText(blog.getString("text"));
			newBlog.setInReplyToStatusID(blog.getLong("in_reply_to_status_id"));
			newBlog.setInReplyToUserID(blog.getLong("in_reply_to_user_id"));
			newBlog.setInReplyToScreenName(blog.getString("in_reply_to_screen_name"));
			newBlog.setInReplyToStatusText(blog.getString("in_reply_to_status_text"));
			newBlog.setPic(blog.getString("small_pic"), blog.getString("middle_pic"), blog.getString("original_pic"));

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
		return false;
	}

}
