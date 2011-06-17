package com.ade.soda;

import java.util.Iterator;
import java.util.Set;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.SiteManager;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class BlogAdapter implements ListAdapter {
	private Set<Blog> blogs;
	private Context context;
	
	public BlogAdapter(Set<Blog> blogs,Context context){
		this.blogs=blogs;
		this.context=context;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int arg0) {
		return true;
	}

	@Override
	public int getCount() {
		return blogs.size();
	}

	@Override
	public Blog getItem(int position) {
		if (position<0 || position>=getCount())
			return null;
	
		if (blogs!=null){
			Iterator<Blog> iterator=blogs.iterator();
			int i=0;
			while(iterator.hasNext()){
				if (i==position){
					return iterator.next();
				}
				else{
					iterator.next();
					i++;
				}
			}
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (getItem(position)!=null)
			return ((Blog)getItem(position)).getID();
		else
			return 0;
	}

	@Override
	public int getItemViewType(int position) {
		return Adapter.IGNORE_ITEM_VIEW_TYPE;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=null;
		if (convertView==null){
			LayoutInflater li=LayoutInflater.from(context);
			view=li.inflate(R.layout.blogview, null);
		}
		else{
			view=convertView;
		}

		Blog blog=getItem(position);
		if (blog!=null){
			updateBlogView(view, blog);
			
			if (blog.isHaveRetweetedBlog()&& blog.getInReplyBlogText().length()>0){
				updateRetweeteBlogView(view, blog);
			}
			else{
				view.findViewById(R.id.reBlog).setVisibility(View.GONE);
				view.findViewById(R.id.reImage).setVisibility(View.GONE);
			}
		}
		view.setTag(blog);
		return view;
	}

	/**
	 * @param view
	 * @param blog
	 */
	private void updateBlogView(View view, Blog blog) {
		TextView userName=(TextView)view.findViewById(R.id.userName);
		TextView creatAtText=(TextView)view.findViewById(R.id.creatAtText);
		BlogTextView blogText=(BlogTextView)view.findViewById(R.id.blogText);
		WebView profileImage=(WebView)view.findViewById(R.id.profileImage);
		ImageView vImage=(ImageView)view.findViewById(R.id.vImage);
		WebView smallImage=(WebView)view.findViewById(R.id.smallImage);
		BlogTextView sourceText=(BlogTextView)view.findViewById(R.id.sourceText);

		userName.setText(blog.getUser().getScreenName());
		creatAtText.setText(blogTimeAdapter(blog));
		sourceText.setText(context.getString(R.string.from)+blog.getSource());
			
		blogText.setText(blog.getText(),
				SiteManager.getInstance().getSiteByID(blog.getSiteID()).getFaceMap());
		profileImage.loadUrl(blog.getUser().getProfileImageUrl());
		
		if (!blog.getUser().isVerified()){
			vImage.setVisibility(View.INVISIBLE);
		}
		
		if (blog.getSmallPic().length()>0){
			smallImage.loadUrl(blog.getSmallPic());
		}
		else{
			smallImage.setVisibility(View.GONE);
		}
	}

	/**
	 * @param view
	 * @param blog
	 */
	private void updateRetweeteBlogView(View view, Blog blog) {
		BlogTextView reBlogText=(BlogTextView)view.findViewById(R.id.reBlogText);
		WebView reImage=(WebView)view.findViewById(R.id.reImage);
		
		if (blog.getInReplyBlogText().length()>0){
			if (blog.getInReplyUserScreenName().length()>0){
				reBlogText.setText("@"+blog.getInReplyUserScreenName()+": "+blog.getInReplyBlogText(),
					SiteManager.getInstance().getSiteByID(blog.getSiteID()).getFaceMap());
			}
			else{
				reBlogText.setText(blog.getInReplyBlogText(),
					SiteManager.getInstance().getSiteByID(blog.getSiteID()).getFaceMap());
			}
		}
		else{
			reBlogText.setVisibility(View.GONE);
		}
		
		if (blog.getRetweetedBlog().getSmallPic().length()>0){
			reImage.loadUrl(blog.getRetweetedBlog().getSmallPic());
		}
		else{
			reImage.setVisibility(View.GONE);
		}
	}
	
	private String blogTimeAdapter(Blog blog){
		//设置显示微博时间格式，年月部分暂不计入格式
		int blogSeconds=blog.getCreatedAt().getSeconds();
		int blogMinutes=blog.getCreatedAt().getMinutes();
		int blogHours=blog.getCreatedAt().getHours();
		int blogDay=blog.getCreatedAt().getDate();
		//		int blogMouth=blog.getCreatedAt().getMonth();
		//		int blogYear=blog.getCreatedAt().getYear();
		Time systemTime=new Time(); 
		systemTime.setToNow();
		int systemSeconds=systemTime.second;
		int systemMinutes=systemTime.minute;
		int systemHours=systemTime.hour;
		int systemDay=systemTime.monthDay;
		//		int systemMouth=systemTime.month;
		//		int systemYear=systemTime.year;
		int temp;//时间差
		StringBuilder blogTime=new StringBuilder("约");
		//		if(blogYear==systemYear&&blogMouth==systemMouth){
		if(blogDay==systemDay){
			if(blogHours==systemHours){
				if(blogMinutes==systemMinutes){
					temp=systemSeconds-blogSeconds;
					blogTime.append(temp+"秒前");
				}
				else{
					temp=systemMinutes-blogMinutes;
					blogTime.append(temp+"分钟前");
				}
			}
			else{
				temp=systemHours-blogHours;
				blogTime.append(temp+"小时前");
			}
		}
		else{
			temp=systemDay-blogDay;
			blogTime.append(temp+"天前");
		}
		//		}
		//		else{
		//			blogTime.append("很久以前");
		//		}
		return blogTime.toString();
	}

	@Override
	public int getViewTypeCount() {
		return 1;  //Only One type of view
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

}
