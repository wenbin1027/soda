package com.ade.parser;

import org.json.JSONException;
import org.json.JSONObject;
import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public class SohuUploadParser extends SohuBasicParser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
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
			if (parseBlog(blog,newBlog)){
				User blogUser=new User();
				JSONObject user=blog.getJSONObject("user");	
				if (parseUser(user,blogUser)){
					newBlog.setUser(blogUser);
					newBlog.setSiteID(site.getSiteID());
					site.addBlog(newBlog);
				}
			}
		}
		return true;
	}

}
