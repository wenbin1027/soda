package com.ade.soda;

import java.util.Set;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.SiteListener;
import com.ade.site.SiteManager;
import com.ade.site.User;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class BlogListView extends ListView implements SiteListener {
	private final int BLOGCOUNTPERPAGE=10;
	private final int BEGIN = 0;
	private final int ERROR = 1;
	private final int END = 2;
	private User user = new User();
	private Site site;
	private int blogPage=0;
	private Dialog progressDlg;

	private Handler mainHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case BEGIN:
				if (progressDlg==null){
					progressDlg=ProgressDialog.show(
							getContext(), 
							getResources().getString(R.string.waitDialogTitle),
							getResources().getString(R.string.waitDialogMessage),
							true,true,
							new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									site.abort();
								}
							}
							);
				}
				break;
			case END:
				if (progressDlg!=null){
					progressDlg.dismiss();
					progressDlg=null;
				}
				Set<Blog> blogs=site.getBlogs();
				setAdapter(new BlogAdapter(blogs,getContext()));
				break;
			case ERROR:
				if (progressDlg!=null){
					progressDlg.dismiss();
					progressDlg=null;
				}
				break;
			}
			return false;
		}
	});
	
	public BlogListView(Context context) {
		super(context);
	}

	public BlogListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BlogListView(Context context, AttributeSet attrs, int defStyle) {
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
				blogPage=0;
				site.friendsTimeline(BLOGCOUNTPERPAGE,blogPage);
			}			
		});
		View footerView=li.inflate(R.layout.bloglistfooter, null);
		footerView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				blogPage++;
				site.friendsTimeline(BLOGCOUNTPERPAGE,blogPage);
			}			
		});
		addHeaderView(headerView);
		addFooterView(footerView);
		
		site = SiteManager.getInstance().getSite(SiteManager.SOHU);
		site.addListener(this);
		user.setAccessToken("46b571ca341cfeb4737f419ed4ce0392");  
		user.setAccessSecret("920143c011048ab9e4c8904440e7ed1a");
		
//		//SINA token
//		user.setAccessToken("4207a6817f50785a07f456da1f4d20b7");  
//		user.setAccessSecret("751c76001bcef5b3c225dbd942c33eaa");
		
		site.logIn(user);
		blogPage=0;
		site.friendsTimeline(BLOGCOUNTPERPAGE,blogPage);
	}

	@Override
	public void onBeginRequest() {
		mainHandler.sendEmptyMessage(BEGIN);
	}

	@Override
	public void onError(String errorMessage) {
		mainHandler.sendEmptyMessage(ERROR);
	}

	@Override
	public void onResponsed() {
		mainHandler.sendEmptyMessage(END);
	}
}
