package com.ade.soda;

import java.util.Iterator;
import java.util.Set;
import com.ade.site.Blog;
import com.ade.site.SiteManager;
import com.ade.site.Site;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TabHost; 
import android.widget.TabWidget;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {
	private static final String SOHU = "sohu";
	private static final String SINA = "sina";
	private static final int WRITEREQUEST=1;
	private static final int SETREQUEST=2;
	private final String TAG = "MainActivity";
	private int currentSite = SiteManager.SINA;
	private BlogListView sinaListView;
	private BlogListView sohuListView;
	private TabHost tabHost;
	private SiteManager siteMgr;
	private TextView usernameTextview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		siteMgr=SiteManager.getInstance();
		siteMgr.loadSites(this);
		//siteMgr=SiteManager.getInstance(this);
		
		usernameTextview=(TextView)findViewById(R.id.TextViewUsername);
		updateUserNameTextView();

        tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(
        		tabHost.newTabSpec(SINA).setIndicator(
        				null, 
        				getResources().getDrawable(R.drawable.sina))
        				.setContent(R.id.tab_sina));   
        tabHost.addTab(
        		tabHost.newTabSpec(SOHU).setIndicator(
        				null, 
        				getResources().getDrawable(R.drawable.sohu))
        				.setContent(R.id.tab_sohu));   

        tabHost.setOnTabChangedListener(new OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equalsIgnoreCase(SINA)){
					currentSite=SiteManager.SINA;
					updateUserNameTextView();
				}
				else if (tabId.equalsIgnoreCase(SOHU)){
					currentSite=SiteManager.SOHU;
					updateUserNameTextView();
				}
			}
        });
		tabHost.setCurrentTabByTag(SINA);
        
		sinaListView=(BlogListView)findViewById(R.id.SinaList);
		sinaListView.setOnItemClickListener(this);
		sinaListView.init(siteMgr.getSiteByID(SiteManager.SINA));

		sohuListView=(BlogListView)findViewById(R.id.SohuList);
		sohuListView.setOnItemClickListener(this);
		sohuListView.init(siteMgr.getSiteByID(SiteManager.SOHU));	
		
		findViewById(R.id.BtnWrite).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentWrite = new Intent(MainActivity.this,WriteActivity.class);
				intentWrite.putExtra("site", currentSite);
				
				MainActivity.this.startActivityForResult(intentWrite, WRITEREQUEST);
			}
		});
		
		findViewById(R.id.BtnRefresh).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshBlogList();
			}
		});
	}

	private void updateUserNameTextView() {
		if (siteMgr.getSiteByID(currentSite).isLoggedIn())
			usernameTextview.setText(siteMgr.getSiteByID(currentSite).getLoggedInUser().getScreenName());
		else
			usernameTextview.setText(R.string.unAuthUser);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.setmenu:
			Intent intentSet = new Intent(MainActivity.this,
			SetActivity.class);
			intentSet.putExtra("site", currentSite);
			MainActivity.this.startActivityForResult(intentSet,SETREQUEST);
			return true;
		case R.id.aboutmenu:
			//TODO: 
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Site site=siteMgr.getSiteByID(currentSite);
		Set<Blog> blogs=site.getBlogs();
		Iterator<Blog> iterator=blogs.iterator();
		int i=1;
		while(iterator.hasNext()){
			if (i==arg2){
				Intent intentSet = new Intent(MainActivity.this,
						REandFWActivity.class);
				intentSet.putExtra("CurrentBlog", iterator.next());
				intentSet.putExtra("siteID", currentSite);
				MainActivity.this.startActivity(intentSet);
				break;
			}
			else{
				iterator.next();
				i++;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case WRITEREQUEST:
			if (resultCode==RESULT_OK){
				refreshBlogList();
			}
			break;
		case SETREQUEST:
			if (resultCode==RESULT_OK){
				updateUserNameTextView();
				refreshBlogList();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		siteMgr.saveSites(this);
		super.onBackPressed();
	}

	/**
	 * 
	 */
	private void refreshBlogList() {
		switch(currentSite){
		case SiteManager.SINA:
			sinaListView.refresh();
			break;
		case SiteManager.SOHU:
			sohuListView.refresh();
			break;
		}
	}
}
