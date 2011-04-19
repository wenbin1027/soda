package com.ade.site;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;

import com.ade.restapi.FriendsTimelineInterface;
import com.ade.net.HttpNet;
import com.ade.net.IHttpListener;
import com.ade.restapi.UpdateInterface;
import com.ade.restapi.UploadInterface;
import com.ade.parser.Parser;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:51
 */
public abstract class Site implements IHttpListener{
	
	protected String appKey;
	protected String appSecret;
	protected String boundary="SodaOfAde93859032";
	protected List<Blog> blogs;
	private FriendsTimelineInterface friendsTimeline;
	protected HttpNet httpNet;
	protected User loggedInUser;
	protected String name;
	protected String rootUrl;
	protected String source;
	private UpdateInterface updateInterface;
	private UploadInterface uploadInterface;
	protected List<SiteListener> listeners;
	protected boolean isLoggedIn=false;
	protected String oauthRequestUrl;
	protected String oauthUrl;
	protected String oauthAccessUrl;

	public Site() {
		onConstruct();
	}
	
	protected abstract void onConstruct();
	
	public void addListener(SiteListener listener){
		if (listeners==null)
			listeners=new ArrayList<SiteListener>();
		listeners.add(listener);
	}
	
	public void removeListener(SiteListener listener){
		if (listener !=null && listeners!=null){
			listeners.remove(listener);
		}
	}
	
	protected void notifyBegin(){
		if (listeners!=null){
			for(SiteListener listener:listeners){
				listener.onBeginRequest();
			}
		}
	}
	
	protected void notifyError(String errorMessage){
		if (listeners!=null){
			for(SiteListener listener:listeners){
				listener.onError(errorMessage);
			}
		}
	}
	
	protected void notifyResponse(){
		if (listeners!=null){
			for(SiteListener listener:listeners){
				listener.onResponsed();
			}
		}
	}
	
	/**
	 * @return the boundary
	 */
	public String getBoundary() {
		return boundary;
	}
	
	/**
	 * @param friendsTimeline the friendsTimeline to set
	 */
	public void setFriendsTimeline(FriendsTimelineInterface friendsTimeline) {
		this.friendsTimeline = friendsTimeline;
	}
	
	/**
	 * @param updateInterface the updateInterface to set
	 */
	public void setUpdateInterface(UpdateInterface updateInterface) {
		this.updateInterface = updateInterface;
	}

	/**
	 * @param uploadInterface the uploadInterface to set
	 */
	public void setUploadInterface(UploadInterface uploadInterface) {
		this.uploadInterface = uploadInterface;
	}

	public void friendsTimeline(){
		if (friendsTimeline!=null){
			httpNet=new HttpNet();
			httpNet.setListener(this);
			httpNet.request(friendsTimeline.getRequest(10, -1, this));
		}
	}

	public String getAppKey(){
		return appKey;
	}

	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}
	

	public String getAccessKey(){
		if (loggedInUser!=null)
			return loggedInUser.getAccessToken();
		else
			return "";
	}

	/**
	 * @return the appSecret
	 */
	public String getAccessSecret() {
		if (loggedInUser!=null)
			return loggedInUser.getAccessSecret();
		else
			return "";
	}
	
	public List<Blog> getBlogs(){
		return blogs;
	}

	public User getLoggedInUser(){
		if (isLoggedIn)
			return loggedInUser;
		else
			return null;
	}

	public String getName(){
		return name;
	}

	public String getRootUrl(){
		return rootUrl;
	}

	public boolean isLoggedIn(){
		return isLoggedIn;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 */
	public void logIn(User user){
		this.loggedInUser=user;
		//friendsTimeline();
	}

	/**
	 * 
	 * @param text
	 */
	public void updateText(String text){
		if (updateInterface!=null){
			httpNet=new HttpNet();
			httpNet.setListener(this);
			httpNet.request(updateInterface.getRequest(text, this));
		}
	}

	/**
	 * 
	 * @param text
	 * @param fileName
	 */
	public void uploadImage( String fileName,String text){
		if (uploadInterface!=null){
			httpNet=new HttpNet();
			httpNet.setListener(this);
			httpNet.request(uploadInterface.getRequest(fileName,text, this));
		}
	}
	

	@Override
	public void onBeginRequest() {
		notifyBegin();
	}

	public String getOauthRequestUrl() {
		return getRootUrl()+oauthRequestUrl;
	}

	public String getOauthUrl() {
		return getRootUrl()+oauthUrl;
	}

	public String getOauthAccessUrl() {
		return getRootUrl()+oauthAccessUrl;
	}
}