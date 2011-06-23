package com.ade.parser;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public abstract class TencentBasicParser extends Parser {
	
	public boolean parseUser(JSONObject data,User user) throws JSONException{
		if (user==null || data==null)
			return false;
		user.setScreenName(data.getString("nick"));
		user.setName(data.getString("name"));
		user.setLocation(data.getString("location"));
		user.setDescription(data.getString("introduction"));
		user.setProfileImageUrl(data.getString("head"));
		user.setGender(""+data.getInt("sex"));
		user.setFollowersCount(data.getLong("fansnum"));
		user.setFriendsCount(data.getLong("idolnum"));
		user.setBlogsCount(data.getLong("tweetnum"));
		user.setVerified(data.getInt("isvip")!=0);
		return true;
	}
	
	public boolean parseBlog(JSONObject data,Blog blog) throws JSONException{
		if (blog==null || data==null)
			return false;
		blog.setCreatedAt(new Date(data.getString("timestamp")));
		blog.setID(data.getLong("id"));
		blog.setText(data.getString("text"));
		blog.setSource(data.getString("from"));
		return true;
	}

	@Override
	protected abstract boolean onParse(String in, Site site) throws JSONException;

}
