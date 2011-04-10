package com.ade.site;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:33:49
 */
public class Blog {

	private Date createdAt;
	private long ID;
	private Blog retweetedBlog;
	private Site site;
	private String text;
	private User user;
	public User m_User;

	public Blog(){

	}

	public void finalize() throws Throwable {

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
		return null;
	}

	public long getID(){
		return 0;
	}

	public Blog getRetweetedBlog(){
		return null;
	}

	public Site getSite(){
		return null;
	}

	public String getText(){
		return "";
	}

	public User getUser(){
		return null;
	}

}