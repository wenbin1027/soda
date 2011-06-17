package com.ade.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;


public class SinaFriendsTimelineParser extends SinaBasicParser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		/*
		[
		    {
		        "created_at" : "Tue Nov 30 16:21:13 +0800 2010",
		        "text" : "转发微博。",
		        "truncated" : false,
		        "retweeted_status" : 
		        {
		            "created_at" : "Tue Nov 30 16:05:41 +0800 2010",
		            "text" : "对待事物的出发点、立足点，决定着事物的发展及发展后的结果。这个结果实际也是一种相对而言的成败。世俗间追求这种成败却又调整不好出发点，更是找不到立足点。所以成也好败也好，放下了最好。",
		            "truncated" : false,
		            "in_reply_to_status_id" : "",
		            "annotations" : 
		            [

		            ],
		            "in_reply_to_screen_name" : "",
		            "geo" : null,
		            "user" : 
		            {
		                "name" : "归元隆印",
		                "domain" : "",
		                "geo_enabled" : true,
		                "followers_count" : 66710,
		                "statuses_count" : 77,
		                "favourites_count" : 0,
		                "city" : "1",
		                "description" : "心存慈悲 身奉善行 出世入世 修己助人 归元禅寺官方网站：http://www.guiyuanchansi.net",
		                "verified" : true,
		                "id" : 1799833402,
		                "gender" : "m",
		                "friends_count" : 4,
		                "screen_name" : "归元隆印",
		                "allow_all_act_msg" : false,
		                "following" : false,
		                "url" : "http://1",
		                "profile_image_url" : "http://tp3.sinaimg.cn/1799833402/50/1283207796",
		                "created_at" : "Tue Aug 24 00:00:00 +0800 2010",
		                "province" : "42",
		                "location" : "湖北 武汉"
		            },
		            "favorited" : false,
		            "in_reply_to_user_id" : "",
		            "id" : 3980364843,
		            "source" : "<a href=\"http://t.sina.com.cn\" rel=\"nofollow\">新浪微博</a>"
		        },
		        "in_reply_to_status_id" : "",
		        "annotations" : 
		        [

		        ],
		        "in_reply_to_screen_name" : "",
		        "geo" : null,
		        "user" : 
		        {
		            "name" : "半拉拖鞋",
		            "domain" : "banlatuoxie",
		            "geo_enabled" : true,
		            "followers_count" : 56,
		            "statuses_count" : 333,
		            "favourites_count" : 1,
		            "city" : "5",
		            "description" : "在这里，我只管把话发出去，有没有人理就不管我的事了！",
		            "verified" : false,
		            "id" : 1799824787,
		            "gender" : "m",
		            "friends_count" : 76,
		            "screen_name" : "半拉拖鞋",
		            "allow_all_act_msg" : false,
		            "following" : false,
		            "url" : "http://blog.sina.com.cn/lingdianjingq",
		            "profile_image_url" : "http://tp4.sinaimg.cn/1799824787/50/1289443070/1",
		            "created_at" : "Sun Sep 05 00:00:00 +0800 2010",
		            "province" : "11",
		            "location" : "北京 朝阳区"
		        },
		        "favorited" : false,
		        "in_reply_to_user_id" : "",
		        "id" : 3980654229,
		        "source" : "<a href=\"http://t.sina.com.cn\" rel=\"nofollow\">新浪微博</a>"
		    },
		...
		]
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

					boolean hasRetweeted ;
					hasRetweeted = blog.has("retweeted_status");
					if ( hasRetweeted ){
						JSONObject retBlog;
						retBlog = blog.getJSONObject("retweeted_status");
						Blog myRetBlog = new Blog();
						if (!parseBlog(retBlog,myRetBlog))
							continue;
							
						//retweeted user data
						User myRetUser = new User();
						JSONObject retUser=retBlog.getJSONObject("user");
						if (parseUser(retUser,myRetUser))
							myRetBlog.setUser(myRetUser);
						else
							continue;
							
						newBlog.setRetweetedBlog(myRetBlog);
					}
					
					//User数据部分
					User blogUser=new User();
					JSONObject user=blog.getJSONObject("user");
					if (parseUser(user,blogUser))
						newBlog.setUser(blogUser);
					else
						continue;
					
					newBlog.setSiteID(site.getSiteID());
					site.addBlog(newBlog);
				}
			}
		}
		
		
		return false;
	}

}
