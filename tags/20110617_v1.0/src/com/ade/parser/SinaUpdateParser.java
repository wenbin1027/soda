package com.ade.parser;

import org.json.JSONException;
import org.json.JSONObject;
import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public class SinaUpdateParser extends SinaBasicParser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
	/*
		{
		    "created_at" : "Mon Dec 13 14:56:03 +0800 2010",
		    "text" : "abc",
		    "truncated" : false,
		    "in_reply_to_status_id" : "",
		    "annotations" : 
		    [
		        {
		            "type2" : 123
		        }
		    ],
		    "in_reply_to_screen_name" : "",
		    "geo" : null,
		    "user" : 
		    {
		        "name" : "siegetest2",
		        "domain" : "",
		        "geo_enabled" : true,
		        "followers_count" : 0,
		        "statuses_count" : 3,
		        "favourites_count" : 0,
		        "city" : "8",
		        "description" : "",
		        "verified" : false,
		        "id" : 1854835127,
		        "gender" : "m",
		        "friends_count" : 20,
		        "screen_name" : "siegetest2",
		        "allow_all_act_msg" : false,
		        "following" : false,
		        "url" : "",
		        "profile_image_url" : "http://tp4.sinaimg.cn/1854835127/50/1291709848/1",
		        "created_at" : "Thu Nov 11 00:00:00 +0800 2010",
		        "province" : "11",
		        "location" : "北京 海淀区"
		    },
		    "favorited" : false,
		    "in_reply_to_user_id" : "",
		    "id" : 4288574373,
		    "source" : "<a href=\"http://open.t.sina.com.cn\" rel=\"nofollow\">微博开放平台接口</a>"
		}
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
