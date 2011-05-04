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
	private String text="";
	private String SmallPic="";
	private String MiddlePic="";
	private String OriginalPic="";
	private User user;
	private String source="";

	public Blog(){

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

	public boolean isHaveRetweetedBlog(){
		return retweetedBlog!=null;
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
	
	public String getSmallPic(){
		return SmallPic;
	}
	
	public String getMiddlePic(){
		return MiddlePic;
	}

	public String getOriginalPic(){
		return OriginalPic;
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
	
	public long getInReplyBlogID(){
		if (retweetedBlog!=null)
			return retweetedBlog.getID();
		else
			return 0;
	}	
	
	public long getInReplyUserID(){
		if (retweetedBlog!=null && retweetedBlog.getUser()!=null)
			return retweetedBlog.getUser().getID();
		else
			return 0;
	}
		
	public String getInReplyUserScreenName(){
		if (retweetedBlog!=null && retweetedBlog.getUser()!=null)
			return retweetedBlog.getUser().getScreenName();
		else
			return "";		
	}
	
	public String getInReplyBlogText(){
		if (retweetedBlog!=null)
			return retweetedBlog.getText();
		else
			return "";	
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
				return -1;
			else if (this.ID<blog.ID)
				return 1;
			else
				return 0;
		}
		else{
			return 0;
		}
	}

	@Override
	public String toString() {
		return "Blog [ID=" + ID + ", SmallPic=" + SmallPic + ", createdAt="
				+ createdAt + ", source=" + source + ", text=" + text + "]";
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

}