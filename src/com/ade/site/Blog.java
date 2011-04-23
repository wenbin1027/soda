package com.ade.site;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 中文注释
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:49 
 */
public class Blog implements Serializable,Comparable<Blog>{

	private static final long serialVersionUID = 1595610101629159577L;
	private Date createdAt;
	private long ID;
	private Blog retweetedBlog;
	private Site site;
	private String text;
	private long InReplyToStatusID;
	private long InReplyToUserID;
	private String InReplyToScreenName;
	private String InReplyToStatusText;
	private String SmallPic;
	private String MiddlePic;
	private String OriginalPic;
	private User user;

	public Blog(){

	}

	/**
	 * 
	 * @param site
	 */
	public Blog(Site site){

	}

	/**
	 * 
	 * @param string
	 * @param site
	 */
	public static List<Blog> constructBlog(String string, Site site){
		return null;
	}

	public Date getCreatedAt(){
		return createdAt;
	}

	public long getID(){
		return ID;
	}

	public Blog getRetweetedBlog(){
		return retweetedBlog;
	}

	public Site getSite(){
		return site;
	}

	public String getText(){
		return text;
	}

	public User getUser(){
		return user;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public void setRetweetedBlog(Blog retweetedBlog) {
		this.retweetedBlog = retweetedBlog;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setInReplyToStatusID(long inReplyToStatusID){
		InReplyToStatusID = inReplyToStatusID;
	}	
	public void setInReplyToUserID(long inReplyToUserID){
		InReplyToUserID = inReplyToUserID;
	}
		
	public void setInReplyToScreenName(String inReplyToScreenName){
		this.InReplyToScreenName = inReplyToScreenName;
	}
	
	public void setInReplyToStatusText(String inReplyToStatusText){
		this.InReplyToStatusText = inReplyToStatusText;
	}
	
	public void setPic(String smallPic,String middlePic,String originalPic){
		this.SmallPic=smallPic;
		this.MiddlePic=middlePic;
		this.OriginalPic=originalPic;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int compareTo(Blog another) {
		if (another instanceof Blog || another==null){
			Blog blog=(Blog)another;
			if (this.ID>blog.ID)
				return 1;
			else if (this.ID<blog.ID)
				return -1;
			else
				return 0;
		}
		else{
			return 0;
		}
	}

}