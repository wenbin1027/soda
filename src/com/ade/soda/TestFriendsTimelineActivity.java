package com.ade.soda;

import java.util.Set;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.SiteListener;
import com.ade.site.SiteManager;
import com.ade.site.User;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**本例供测试SOHU微博的好友列表功能，供参考
 * @author Administrator
 *
 */
public class TestFriendsTimelineActivity extends Activity 
implements SiteListener{
	private final int BLOGCOUNTPERPAGE=10;
	private final int BEGIN = 0;
	private final int ERROR = 1;
	private final int END = 2;
	private User user = new User();
	private Site site;
	private int currentSite = SiteManager.SOHU;
	private ListView listView;
	private Dialog progressDlg;
	private int blogPage=0;
	private Handler mainHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case BEGIN:
				if (progressDlg==null){
					progressDlg=ProgressDialog.show(
							TestFriendsTimelineActivity.this, 
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
				listView.setAdapter(new BlogAdapter(blogs,TestFriendsTimelineActivity.this));
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testfriendstimeline);
		listView=(ListView)findViewById(R.id.friendsTimelineList);
		LayoutInflater li=LayoutInflater.from(this);
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
		listView.addHeaderView(headerView);
		listView.addFooterView(footerView);
			
		SiteManager.getInstance().setContext(TestFriendsTimelineActivity.this);
		SiteManager.getInstance().loadSites();
		site = SiteManager.getInstance().getSites().get(currentSite);
		site.addListener(TestFriendsTimelineActivity.this);
		user.setAccessToken("46b571ca341cfeb4737f419ed4ce0392");  
		user.setAccessSecret("920143c011048ab9e4c8904440e7ed1a");
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
