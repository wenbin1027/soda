package com.ade.site;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:53
 */
public class User implements Serializable{
	private static final long serialVersionUID = 4621473305493545567L;
	private List<Blog> blogs;
	private Date createdAt;
	private String description;
	private long followersCount;
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

	public List<Blog> getBlogs(){
		return blogs;
	}

	public long getBlogsCount(){
		if (blogs!=null)
			return blogs.size();
		else
			return 0;
	}

	public Date getCreatedAt(){
		return createdAt;
	}

	public String getDescription(){
		return description;
	}

	public long getFollowersCount(){
		return followersCount;
	}

	public String getGender(){
		return gender;
	}

	public long getID(){
		return ID;
	}

	public String getLocation(){
		return location;
	}

	public String getName(){
		return name;
	}

	public String getProfileImageUrl(){
		return profileImageUrl;
	}

	public String getScreenName(){
		return screenName;
	}

	public String getUrl(){
		return url;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isVerified(){
		return verified;
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", accessSecret=" + accessSecret
				+ ", accessToken=" + accessToken + ", blogs=" + blogs
				+ ", createdAt=" + createdAt + ", description=" + description
				+ ", followersCount=" + followersCount 
				+ ", gender=" + gender + ", location="
				+ location + ", name=" + name + ", profileImageUrl="
				+ profileImageUrl + ", screenName=" + screenName + ", url="
				+ url + ", verified=" + verified + "]";
	}

}