package com.ade.soda;

import com.ade.site.Blog;
import com.ade.site.SiteManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TabHost; 
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {
	private static final String SOHU = "sohu";
	private static final String SINA = "sina";
	private static final String ALL = "all";
	private static final int WRITEREQUEST=1;
	private static final int SETREQUEST=2;
	private BlogListView sinaListView;
	private BlogListView sohuListView;
	private MixBlogListView allListView;
	private TabHost tabHost;
	private SiteManager siteMgr;
	private TextView usernameTextview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.this.startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
		setContentView(R.layout.main);
		siteMgr=SiteManager.getInstance();
		siteMgr.loadSites(this);
		
		usernameTextview=(TextView)findViewById(R.id.TextViewUsername);

        tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(
        		tabHost.newTabSpec(ALL).setIndicator(
        				null, 
        				getResources().getDrawable(R.drawable.soda))
        				.setContent(R.id.tab_mix));   
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
				updateUserNameTextView();
			}
        });
		tabHost.setCurrentTabByTag(ALL);
        
		sinaListView=(BlogListView)findViewById(R.id.SinaList);
		sinaListView.setOnItemClickListener(this);
		sinaListView.init(siteMgr.getSiteByID(SiteManager.SINA));

		sohuListView=(BlogListView)findViewById(R.id.SohuList);
		sohuListView.setOnItemClickListener(this);
		sohuListView.init(siteMgr.getSiteByID(SiteManager.SOHU));
		
		allListView=(MixBlogListView)findViewById(R.id.AllList);
		allListView.setOnItemClickListener(this);
		allListView.addBlogListView(sinaListView);
		allListView.addBlogListView(sohuListView);
		allListView.init();
		
		findViewById(R.id.BtnWrite).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String tab=tabHost.getCurrentTabTag();
				Intent intentWrite = new Intent(MainActivity.this,WriteActivity.class);
				if (tab==SINA)
					intentWrite.putExtra("site", SiteManager.SINA);
				else if (tab==SOHU)
					intentWrite.putExtra("site", SiteManager.SOHU);
				
				MainActivity.this.startActivityForResult(intentWrite, WRITEREQUEST);
			}
		});
		
		findViewById(R.id.BtnRefresh).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshBlogList();
			}
		});
		
		updateUserNameTextView();
	}

	private void updateUserNameTextView() {
		String tab=tabHost.getCurrentTabTag();
		if (tab==SINA){
			if (siteMgr.getSiteByID(SiteManager.SINA).isLoggedIn()){
				usernameTextview.setText(siteMgr.getSiteByID(SiteManager.SINA).getLoggedInUser().getScreenName());
			}
			else{
				usernameTextview.setText(R.string.unAuthUser);
			}
		}
		else if (tab==SOHU){
			if (siteMgr.getSiteByID(SiteManager.SOHU).isLoggedIn()){
				usernameTextview.setText(siteMgr.getSiteByID(SiteManager.SOHU).getLoggedInUser().getScreenName());
			}
			else{
				usernameTextview.setText(R.string.unAuthUser);
			}
		}
		else{
			usernameTextview.setText(null);
		}
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
			MainActivity.this.startActivityForResult(intentSet,SETREQUEST);
			return true;
		case R.id.aboutmenu:
			//字符串封装后会有异常，改进中
			AlertDialog.Builder aboutAlertDialog = new AlertDialog.Builder(this);
			aboutAlertDialog.setTitle("关于我们").setMessage("苏打微博1.0， 由ADE小组倾情打造，谢谢支持！")
			.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) { 
				}}).setCancelable(false).create().show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			//字符串封装后会有异常，改进中
			AlertDialog.Builder exitAlertDialog = new AlertDialog.Builder(this);
			exitAlertDialog.setTitle("退出提示").setMessage("确定要退出苏打微博吗？")
			.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					siteMgr.saveSites(MainActivity.this);
					 finish();   
				}
				})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
					}})
					.setCancelable(false).create().show();
			return true;
		}
		else {     
			return super.onKeyDown(keyCode, event);     
			}     
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intentSet = new Intent(MainActivity.this,
				REandFWActivity.class);
		intentSet.putExtra("CurrentBlog", (Blog)arg1.getTag());
		MainActivity.this.startActivity(intentSet);
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

	/**
	 * 
	 */
	private void refreshBlogList() {
		String tab=tabHost.getCurrentTabTag();
		if (tab==SINA){
			sinaListView.refresh();
		}
		else if (tab==SOHU){
			sohuListView.refresh();
		}
		else{
			sinaListView.refresh();
			sohuListView.refresh();
		}		
	}
}
