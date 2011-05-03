package com.ade.soda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;

import com.ade.restapi.FriendsTimelineInterface;
import com.ade.restapi.SinaFriendsTimeline;
import com.ade.site.Blog;
import com.ade.site.OAuth;
import com.ade.site.SinaSite;
import com.ade.site.SiteListener;
import com.ade.site.SiteManager;
import com.ade.site.SohuSite;
import com.ade.site.Site;
import com.ade.site.User;
import com.ade.util.OAuthUtil;
import com.ade.restapi.UpdateInterface;
import com.ade.restapi.SohuUpdate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TabHost; 
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.app.TabActivity;

public class MainActivity extends Activity implements OnItemClickListener {
	private final String TAG = "MainActivity";
	private final int BLOGCOUNTPERPAGE=10;
	private final int AUTHREQUESTCODE = 0;
	private final int SITEERROR = 0;
	private final int TESTOK = 1;
	private User sinauser = new User();
	private User sohuuser = new User();
	private Site site;
	private int currentSite = SiteManager.SINA; // 测试时注意修改此处为要测的网站
	private View lastClickedView;
	private BlogListView sinaListView;
	private BlogListView sohuListView;
	private TabHost tabHost;
	
/*	private ResponseHandler<String> handler = new ResponseHandler<String>() {

		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpResponse res = response;
			return null;
		}
	};

	private Handler mainHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case SITEERROR:
				String errorMessage = (String) msg.obj;
				Toast.makeText(MainActivity.this,
						errorMessage != null ? errorMessage : "未知错误",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this,
						OAuthActivity.class);
				intent.putExtra("siteID", currentSite);
				intent.putExtra("user", new User());
				startActivityForResult(intent, AUTHREQUESTCODE);
				break;
			case TESTOK:
				Toast.makeText(MainActivity.this, "测试成功", Toast.LENGTH_LONG)
						.show();
				Log.i(TAG, "测试成功");
				Set<Blog> blogs = site.getBlogs();
				sinaListView.setAdapter(new BlogAdapter(blogs,MainActivity.this));
				sohuListView.setAdapter(new BlogAdapter(blogs,MainActivity.this));
				break;
			}
			return false;
		}
	});*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
        tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(
        		tabHost.newTabSpec("sina").setIndicator(
        				SiteManager.getInstance().getSite(SiteManager.SINA).getName(), null)
        				.setContent(R.id.tab_sina));   
        tabHost.addTab(
        		tabHost.newTabSpec("sohu").setIndicator(
        				SiteManager.getInstance().getSite(SiteManager.SOHU).getName(), null)
        				.setContent(R.id.tab_sohu));   

        tabHost.setOnTabChangedListener(new OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equalsIgnoreCase("sina")){
					currentSite=SiteManager.SINA;
				}
				else if (tabId.equalsIgnoreCase("sohu")){
					currentSite=SiteManager.SOHU;
				}
			}
        });
		tabHost.setCurrentTabByTag("sina");
        
		sinaListView=(BlogListView)findViewById(R.id.SinaList);
		sinaListView.setOnItemClickListener(this);
		sinaListView.init(SiteManager.getInstance().getSite(SiteManager.SINA));

		sohuListView=(BlogListView)findViewById(R.id.SohuList);
		sohuListView.setOnItemClickListener(this);
		sohuListView.init(SiteManager.getInstance().getSite(SiteManager.SOHU));	
		
		site = SiteManager.getInstance(MainActivity.this).getSites().get(currentSite);
		
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
					sohuListView.refresh();
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
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == AUTHREQUESTCODE) {
//			if (resultCode == RESULT_OK) {
//				if (data.hasExtra("user")) {
//					switch(currentSite){
//					case SiteManager.SINA:
//						sinauser = (User) data.getSerializableExtra("user");
//						site.logIn(sinauser);
//						if (lastClickedView != null) {
//							lastClickedView.performClick();
//						}
//						break;
//					case SiteManager.SOHU:
//						sohuuser = (User) data.getSerializableExtra("user");
//						site.logIn(sohuuser);
//						if (lastClickedView != null) {
//							lastClickedView.performClick();
//						}
//						break;
//					}
//
//				}
//			}
//			Toast.makeText(this,
//					resultCode == RESULT_OK ? "Auth Success" : "Auth Fail",
//					Toast.LENGTH_LONG).show();
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Site site=SiteManager.getInstance().getSite(currentSite);
		Set<Blog> blogs=site.getBlogs();
		Iterator<Blog> iterator=blogs.iterator();
		int i=1;
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

}
