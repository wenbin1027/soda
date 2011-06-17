package com.ade.soda;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import com.ade.site.Blog;
import com.ade.site.Site;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class MixBlogListView extends ListView implements BlogListViewListener {
	private List<BlogListView> views=new ArrayList<BlogListView>();
	
	public MixBlogListView(Context context) {
		super(context);
	}

	public MixBlogListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MixBlogListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater li=LayoutInflater.from(getContext());
		View headerView=li.inflate(R.layout.bloglistheader, null);
		headerView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				refresh();
			}			
		});
		View footerView=li.inflate(R.layout.bloglistfooter, null);
		footerView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				more();
			}			
		});
		addHeaderView(headerView);
		addFooterView(footerView);
	}

	public void init(){
		onChanged(null);
	}
	
	public void addBlogListView(BlogListView view){
		view.setListener(this);
		views.add(view);
	}
	
	public void removeBlogListView(BlogListView view){
		view.clearListener();
		views.remove(view);
	}
	
	@Override
	public void onChanged(Site site) {
		Set<Blog> blogs=new TreeSet<Blog>();
		for(int i=0;i<views.size();i++){
			if (views.get(i)!=null 
				&& views.get(i).getSite()!=null 
				&& views.get(i).getSite().getBlogs()!=null){
				blogs.addAll(views.get(i).getSite().getBlogs());
			}
		}
		if (blogs.size()>=0){
			setAdapter(new BlogAdapter(blogs,getContext()));
		}
	}
	
	public void refresh() {
		for(BlogListView view:views){
			if (view!=null && view.getSite().isLoggedIn()){
				view.refresh();
			}
		}
	}
	
	public void more() {
		for(BlogListView view:views){
			if (view!=null && view.getSite().isLoggedIn()){
				view.more();
			}
		}
	}
}
