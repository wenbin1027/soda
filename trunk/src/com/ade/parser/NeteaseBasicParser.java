package com.ade.parser;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.User;

public abstract class NeteaseBasicParser extends Parser {
	
	public boolean parseUser(JSONObject data,User user) throws JSONException{
		if (user==null || data==null)
			return false;
		user.setID(data.getLong("id"));
		user.setScreenName(data.getString("screen_name"));
		user.setName(data.getString("name"));
		user.setLocation(data.getString("location"));
		user.setDescription(data.getString("description"));
		user.setUrl(data.getString("url"));
		user.setProfileImageUrl(data.getString("profile_image_url"));
		user.setGender(data.getString("gender"));
		user.setFollowersCount(data.getLong("followers_count"));
		user.setFriendsCount(data.getLong("friends_count"));
		user.setBlogsCount(data.getLong("statuses_count"));
		user.setFavouritesCount(data.getLong("favourites_count"));
		user.setCreatedAt(new Date(data.getString("created_at")));
		user.setFollowing(data.getBoolean("following"));
		user.setVerified(data.getBoolean("verified"));
		return true;
	}
	
	public boolean parseBlog(JSONObject data,Blog blog) throws JSONException{
		if (blog==null || data==null)
			return false;
		blog.setCreatedAt(new Date(data.getString("created_at")));
		blog.setID(data.getLong("id"));
		blog.setText(data.getString("text"));
		blog.setSource(data.getString("source"));

		return true;
	}
	@Override
	protected abstract boolean onParse(String in, Site site) throws JSONException;

}
