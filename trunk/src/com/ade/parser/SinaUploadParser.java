package com.ade.parser;

import org.json.JSONException;
import org.json.JSONObject;
import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public class SinaUploadParser extends SinaBasicParser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		/*
		{

		    "created_at": "Thu Dec 02 17:06:40 +0800 2010",
		    "id": 4023064087,
		    "text": "abc",
		    "source": "微博开放平台接口",
		    "favorited": false,
		    "truncated": false,
		    "in_reply_to_status_id": "",
		    "in_reply_to_user_id": "",
		    "in_reply_to_screen_name": "",
		    "thumbnail_pic": "http://ww2.sinaimg.cn/thumbnail/6b85067djw6dbydsnam1xj.jpg",
		    "bmiddle_pic": "http://ww2.sinaimg.cn/bmiddle/6b85067djw6dbydsnam1xj.jpg",
		    "original_pic": "http://ww2.sinaimg.cn/large/6b85067djw6dbydsnam1xj.jpg",
		    "geo": {
		          "type": "Point",
		          "coordinates": [
		               39.8765,
		               119.5678
		            ]
		      },
		    "user":{
		          "id": 1803880061,
		          "screen_name": "LoopB",
		          "name": "LoopB",
		          "province": "11",
		          "city": "8",
		          "location": "北京 海淀区",
		          "description": "",
		          "url": "",
		          "profile_image_url": "http://tp2.sinaimg.cn/1803880061/50/0/0",
		          "domain": "",
		          "gender": "f",
		          "followers_count": 1,
		          "friends_count": 1,
		          "statuses_count": 14,
		          "favourites_count": 4,
		          "created_at": "Fri Aug 27 00:00:00 +0800 2010",
		          "following": false,
		          "allow_all_act_msg": false,
		          "geo_enabled": true,
		          "verified": false
		      }
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
					site.addBlog(newBlog);
				}
			}
		}
		return true;
	}

}
