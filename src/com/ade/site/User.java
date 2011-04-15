package com.ade.site;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:53
 */
public class User {

	private List<Blog> blogs;
	private long blogsCount;
	private Date createdAt;
	private String description;
	private long followersCount;
	private long friendsCount;
	private String gender;
	private long ID;
	private String location;
	private String name;
	private String profileImageUrl;
	private String screenName;
	private String url;
	private boolean verified;
	private String accessToken;
	private String accessSecret;
	/**
	 * @return the token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param token the token to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the tokenSecret
	 */
	public String getAccessSecret() {
		return accessSecret;
	}

	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}

	public User(){
		
	}

	public User(long id){
		this.ID=id;
	}

	public void finalize() throws Throwable {

	}

	public List<Blog> getBlogs(){
		return null;
	}

	public long getBlogsCount(){
		return 0;
	}

	public Date getCreatedAt(){
		return null;
	}

	public String getDescription(){
		return "";
	}

	public long getFollowersCount(){
		return 0;
	}

	public long getFriendsCount(){
		return 0;
	}

	public String getGender(){
		return "";
	}

	public long getID(){
		return ID;
	}

	public String getLocation(){
		return "";
	}

	public String getName(){
		return "";
	}

	public String getProfileImageUrl(){
		return "";
	}

	public String getScreenName(){
		return "";
	}

	public String getUrl(){
		return "";
	}

	public boolean isVerified(){
		return false;
	}

}