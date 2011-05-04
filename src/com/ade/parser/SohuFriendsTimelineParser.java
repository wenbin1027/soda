package com.ade.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public class SohuFriendsTimelineParser extends SohuBasicParser {

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
			site.setBlogsOriginalData(in);
			for(int i=0;i<blogs.length();i++){
				JSONObject blog=blogs.getJSONObject(i);
				if (blog!=null){
					Blog newBlog=new Blog();
					if (!parseBlog(blog,newBlog))
						continue;
					if (blog.has("in_reply_to_status_id")){
						long inReplyToStatusID=blog.optLong("in_reply_to_status_id");
						long inReplyToUserID=blog.optLong("in_reply_to_user_id");
						String inReplyToScreenName=blog.optString("in_reply_to_screen_name");
						String inReplyToStatusText=blog.optString("in_reply_to_status_text");

						Blog replyBlog=new Blog();
						replyBlog.setID(inReplyToStatusID);
						replyBlog.setText(inReplyToStatusText);

						User replyUser=new User();
						replyUser.setID(inReplyToUserID);
						replyUser.setScreenName(inReplyToScreenName);
						replyBlog.setUser(replyUser);
						
						newBlog.setRetweetedBlog(replyBlog);
					}
					
					//User数据部分
					User blogUser=new User();
					JSONObject user=blog.getJSONObject("user");
					if (!parseUser(user,blogUser))
						continue;
					newBlog.setUser(blogUser);
					site.addBlog(newBlog);
				}
			}
		}
		
		return true;
	}

}
