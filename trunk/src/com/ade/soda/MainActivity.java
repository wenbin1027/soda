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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {
	private final String TAG = "MainActivity";
	private int currentSite = SiteManager.SINA; // 测试时注意修改此处为要测的网站
	private BlogListView sinaListView;
	private BlogListView sohuListView;
	private TabHost tabHost;
	private SiteManager siteMgr;
	private TextView usernameTextview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		siteMgr=SiteManager.getInstance(this);
		
		usernameTextview=(TextView)findViewById(R.id.TextViewUsername);
        usernameTextview.setText(siteMgr.getSite(currentSite).getLoggedInUser().getScreenName());

        tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(
        		tabHost.newTabSpec("sina").setIndicator(
        				siteMgr.getSite(SiteManager.SINA).getName(), null)
        				.setContent(R.id.tab_sina));   
        tabHost.addTab(
        		tabHost.newTabSpec("sohu").setIndicator(
        				siteMgr.getSite(SiteManager.SOHU).getName(), null)
        				.setContent(R.id.tab_sohu));   

        tabHost.setOnTabChangedListener(new OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equalsIgnoreCase("sina")){
					currentSite=SiteManager.SINA;
			        usernameTextview.setText(siteMgr.getSite(SiteManager.SINA).getLoggedInUser().getScreenName());
				}
				else if (tabId.equalsIgnoreCase("sohu")){
					currentSite=SiteManager.SOHU;
			        usernameTextview.setText(siteMgr.getSite(SiteManager.SOHU).getLoggedInUser().getScreenName());
				}
			}
        });
		tabHost.setCurrentTabByTag("sina");
        
		sinaListView=(BlogListView)findViewById(R.id.SinaList);
		sinaListView.setOnItemClickListener(this);
		sinaListView.init(siteMgr.getSite(SiteManager.SINA));

		sohuListView=(BlogListView)findViewById(R.id.SohuList);
		sohuListView.setOnItemClickListener(this);
		sohuListView.init(siteMgr.getSite(SiteManager.SOHU));	
		
		findViewById(R.id.BtnWrite).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentWrite = new Intent(MainActivity.this,WriteActivity.class);
				intentWrite.putExtra("site", currentSite);
				
				MainActivity.this.startActivity(intentWrite);
			}
		});
		
		findViewById(R.id.BtnRefresh).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch(currentSite){
				case SiteManager.SINA:
					sinaListView.refresh();
					break;
				case SiteManager.SOHU:
					sohuListView.refresh();;
				}
			}
		});
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
			MainActivity.this.startActivity(intentSet);
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
		Site site=siteMgr.getSite(currentSite);
		Set<Blog> blogs=site.getBlogs();
		Iterator<Blog> iterator=blogs.iterator();
		int i=0;
		while(iterator.hasNext()){
			if (i==arg2){
				Intent intentSet = new Intent(MainActivity.this,
						REandFWActivity.class);
				intentSet.putExtra("CurrentBlog", iterator.next());
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
	protected void onDestroy() {
		siteMgr.saveSites();
		super.onDestroy();
	}

}
