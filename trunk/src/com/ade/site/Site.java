package com.ade.site;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import android.util.Log;
import com.ade.restapi.FriendsTimelineInterface;
import com.ade.net.HttpNet;
import com.ade.net.IHttpListener;
import com.ade.restapi.UpdateInterface;
import com.ade.restapi.UploadInterface;


/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:51
 */
public abstract class Site implements IHttpListener{
	
	protected String appKey;
	protected String appSecret;
	protected String boundary="SodaOfAde93859032";
	protected Set<Blog> blogs=new TreeSet<Blog>();
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
	protected String proxyHost;
	protected int proxyPort;
	
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

	public void friendsTimeline(int count,int page){
		if (friendsTimeline!=null){
			httpNet=new HttpNet();
			httpNet.setListener(this);
			if (isProxy()){
				httpNet.setProxy(proxyHost, proxyPort);
			}
			httpNet.request(friendsTimeline.getRequest(count, page, this),friendsTimeline.getParser());
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
	
	public Set<Blog> getBlogs(){
		return blogs;
	}
	
	public long getBlogsCount(){
		return blogs.size();
	}
	
	public void addBlog(Blog blog){
		blogs.add(blog);
	}
	
	public void removeBlog(Blog blog){
		blogs.remove(blog);
	}
	
	public Blog getBlogById(long blogID){
		Iterator<Blog> iterator=blogs.iterator();
		Blog ret=null;
		while(iterator.hasNext()){
			ret=iterator.next();
			if (ret.getID()==blogID){
				break;
			}
		}
		return ret;
	}
	
	public Iterator<Blog> getBlogsIterator(){
		return blogs.iterator();
	}
	
	public void clearBlogs(){
		blogs.clear();
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
	}

	/**
	 * 
	 * @param text
	 */
	public void updateText(String text){
		if (updateInterface!=null){
			httpNet=new HttpNet();
			httpNet.setListener(this);
			httpNet.request(updateInterface.getRequest(text, this),updateInterface.getParser());
		}
	}

	/**
	 * 
	 * @param text
	 * @param fileName
	 * @throws IOException 
	 */
	public void uploadImage( String fileName,String text) throws IOException{
		if (uploadInterface!=null){
			httpNet=new HttpNet();
			httpNet.setListener(this);
			try {
				Log.i("Nancy", "Entering into uploadImage");
				httpNet.request(uploadInterface.getRequest(fileName,text, this),uploadInterface.getParser());
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("读取照片文件时出错");
			}
		}
	}
	
	public void abort(){
		if (httpNet!=null){
			httpNet.cancel();
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
	
	public void setProxy(String proxyHost,int port){
		this.proxyHost=proxyHost;
		this.proxyPort=port;
	}
	
	protected boolean isProxy(){
		if (proxyHost!=null)
			return true;
		return false;
	}
}