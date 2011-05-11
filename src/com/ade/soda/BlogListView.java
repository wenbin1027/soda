package com.ade.soda;

import com.ade.site.Site;
import com.ade.site.SiteListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class BlogListView extends ListView implements SiteListener {
	private final int BLOGCOUNTPERPAGE=10;
	private final int BEGIN = 0;
	private final int ERROR = 1;
	private final int END = 2;
	private Site site;
	private int blogPage=1;
	private Dialog progressDlg;
	private BlogListViewListener listener;

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
				site.removeListener(BlogListView.this);
				dismissDlg();
				setAdapter(new BlogAdapter(site.getBlogs(),getContext()));
				if (listener!=null){
					listener.onChanged(site);
				}
				break;
			case ERROR:
				site.removeListener(BlogListView.this);
				dismissDlg();
				if (msg.obj!=null){
					Toast.makeText(getContext(), (String)msg.obj, Toast.LENGTH_SHORT).show();
				}
				break;
			}
			return true;
		}

		/**
		 * 
		 */
		private void dismissDlg() {
			if (progressDlg!=null){
				progressDlg.dismiss();
				progressDlg=null;
			}
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

	public void init(Site site){
		this.site=site;
		if (site!=null && site.getBlogsCount()>0){
			setAdapter(new BlogAdapter(site.getBlogs(),getContext()));
		}
	}
	
	public void refresh() {
		blogPage=1;
		friendsTimeline();
	}

	/**
	 * 
	 */
	private void friendsTimeline() {
		site.addListener(this);

		if (site.isLoggedIn()){
			site.friendsTimeline(BLOGCOUNTPERPAGE,blogPage);
		}
		else{
			Toast.makeText(getContext(), getContext().getText(R.string.unAuthTips), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void more(){
		blogPage++;
		friendsTimeline();		
	}

	public Site getSite(){
		return site;
	}
	
	@Override
	public void onBeginRequest() {
		mainHandler.sendEmptyMessage(BEGIN);
	}

	@Override
	public void onError(String errorMessage) {
		Message msg=new Message();
		msg.what=ERROR;
		msg.obj=errorMessage;
		mainHandler.sendMessage(msg);
	}

	@Override
	public void onResponsed() {
		mainHandler.sendEmptyMessage(END);
	}
	
	public void setListener(BlogListViewListener listener){
		this.listener=listener;
	}
	
	public void clearListener(){
		this.listener=null;
	}
}
