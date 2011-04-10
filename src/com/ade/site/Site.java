package com.ade.site;
import java.util.List;

import com.ade.restapi.FriendsTimelineInterface;
import com.ade.net.HttpNet;
import com.ade.net.IHttpListener;
import com.ade.restapi.UpdateInterface;
import com.ade.restapi.UploadInterface;
import com.ade.parser.Parser;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:33:51
 */
public abstract class Site implements IHttpListener {

	protected String appKey;
	protected String appSecret;
	private List<Blog> blogs;
	private FriendsTimelineInterface friendsTimeline;
	private HttpNet httpNet;
	private User loggedInUser;
	protected String name;
	protected String rootUrl;
	protected String source;
	private UpdateInterface updateInterface;
	private UploadInterface uploadInterface;

	public Site(){

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
			httpNet.request(friendsTimeline.getRequest(-1, 1, this));
		}
	}

	public String getAppKey(){
		return "";
	}

	public List<Blog> getBlogs(){
		return null;
	}

	public User getLoggedInUser(){
		return null;
	}

	public String getName(){
		return "";
	}

	public String getRootUrl(){
		return "";
	}

	public String getSource(){
		return "";
	}

	public boolean isLoggedIn(){
		return false;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 */
	public void logIn(User user){
		this.loggedInUser=user;
	}

	public void myHome(){

	}

	protected abstract void onConstruct();

	/**
	 * 
	 * @param text
	 */
	public void updateText(String text){
		if (updateInterface!=null){
			httpNet=new HttpNet();
			httpNet.request(updateInterface.getRequest("", this));
		}
	}

	/**
	 * 
	 * @param text
	 * @param fileName
	 */
	public void uploadImage(String text, String fileName){

	}

	public void onBeginRequest(){

	}

	public void onResponsed(){

	}

	public void onError(){

	}

}