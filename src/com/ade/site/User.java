package com.ade.site;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:53
 */
public class User implements Serializable{
	private static final long serialVersionUID = 4621473305493545567L;
	private List<Blog> blogs;
	private Date createdAt;
	private String description="";
	private long followersCount;
	private String gender="n";
	private long ID;
	private String location="";
	private String name="";
	private String profileImageUrl="";
	private String screenName="";
	private String url="";
	private boolean verified;
	private String accessToken="";
	private String accessSecret="";
	private long friendsCount;
	private long blogsCount;
	private long favouritesCount;
	private boolean following;
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
				+ ", accessToken=" + accessToken + ", blogsCount=" + blogsCount
				+ ", createdAt=" + createdAt + ", description=" + description
				+ ", favouritesCount=" + favouritesCount + ", followersCount="
				+ followersCount + ", following=" + following
				+ ", friendsCount=" + friendsCount + ", gender=" + gender
				+ ", location=" + location + ", name=" + name
				+ ", profileImageUrl=" + profileImageUrl + ", screenName="
				+ screenName + ", url=" + url + ", verified=" + verified + "]";
	}

	public void setFriendsCount(long friendsCount) {
		this.friendsCount = friendsCount;
	}

	public long getFriendsCount() {
		return friendsCount;
	}

	public void setBlogsCount(long blogsCount) {
		this.blogsCount = blogsCount;
	}

	public long getBlogsCount() {
		return blogsCount;
	}

	public void setFavouritesCount(long favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public long getFavouritesCount() {
		return favouritesCount;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isFollowing() {
		return following;
	}
	
	public void save(FileOutputStream out) throws IOException, JSONException{
		ObjectOutputStream stream=new ObjectOutputStream(out);
		stream.writeObject(this);
	}
}