package com.ade.site;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 中文注释
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:49 
 */
public class Blog implements Serializable{

	private Date createdAt;
	private long ID;
	private Blog retweetedBlog;
	private Site site;
	private String text;
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

	public void setUser(User user) {
		this.user = user;
	}

}