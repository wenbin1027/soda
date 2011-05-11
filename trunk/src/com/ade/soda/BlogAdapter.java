package com.ade.soda;

import java.util.Iterator;
import java.util.Set;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.SiteManager;

import android.content.Context;
import android.database.DataSetObserver;
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
		TextView userName=(TextView)view.findViewById(R.id.userName);
		BlogTextView blogText=(BlogTextView)view.findViewById(R.id.blogText);
		WebView profileImage=(WebView)view.findViewById(R.id.profileImage);
		ImageView vImage=(ImageView)view.findViewById(R.id.vImage);
		WebView smallImage=(WebView)view.findViewById(R.id.smallImage);
		Blog blog=getItem(position);
		if (blog!=null){
			userName.setText(blog.getUser().getScreenName());
			
			blogText.setText(blog.getText(),
					SiteManager.getInstance().getSiteByID(blog.getSiteID()).getFaceMap());
			
			profileImage.loadUrl(blog.getUser().getProfileImageUrl());
			
			if (!blog.getUser().isVerified())
				vImage.setVisibility(View.INVISIBLE);
			if (blog.getSmallPic().length()>0){
				smallImage.loadUrl(blog.getSmallPic());
			}
			else{
				smallImage.setVisibility(View.GONE);
			}
			
			if (blog.isHaveRetweetedBlog()&& blog.getInReplyBlogText().length()>0){
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
			else{
				view.findViewById(R.id.reBlog).setVisibility(View.GONE);
				view.findViewById(R.id.reImage).setVisibility(View.GONE);
			}
		}
		view.setTag(blog);
		return view;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
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
