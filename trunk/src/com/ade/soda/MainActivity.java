package com.ade.soda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TabHost; 
import android.widget.TabHost.OnTabChangeListener;
import android.app.TabActivity;

public class MainActivity extends Activity implements SiteListener {
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
	private ListView SinalistView;
	private ListView SohulistView;
	private int blogPage=0;
	private String SinaAccessToken=null;
	private String SinaAccessSecret=null;
	private String SohuAccessToken=null;
	private String SohuAccessSecret=null;
	private TabHost tabHost;
	
	private ResponseHandler<String> handler = new ResponseHandler<String>() {

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
				intent.putExtra("site", currentSite);
				intent.putExtra("user", new User());
				startActivityForResult(intent, AUTHREQUESTCODE);
				break;
			case TESTOK:
				Toast.makeText(MainActivity.this, "测试成功", Toast.LENGTH_LONG)
						.show();
				Log.i(TAG, "测试成功");
				Set<Blog> blogs = site.getBlogs();
				SinalistView.setAdapter(new BlogAdapter(blogs,MainActivity.this));
				SohulistView.setAdapter(new BlogAdapter(blogs,MainActivity.this));
				break;
			}
			return false;
		}
	});

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
       	currentSite = SiteManager.SINA;
		
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

				}
				else if (tabId.equalsIgnoreCase("sohu")){

				}
			}
        });
        
		SinalistView=(ListView)findViewById(R.id.SinaList);
		SohulistView=(ListView)findViewById(R.id.SohuList);
		
       	currentSite = SiteManager.SINA;
		site = SiteManager.getInstance(MainActivity.this).getSites().get(currentSite);
		site.addListener(MainActivity.this);

		findViewById(R.id.BtnWrite).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intentWrite = new Intent(MainActivity.this,WriteActivity.class);
				intentWrite.putExtra("site", currentSite);
				MainActivity.this.startActivity(intentWrite);
			}
		});

//
//
//        findViewById(R.id.BtnSinaMsg).setOnClickListener(
//                new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                        	currentSite = SiteManager.SINA;
//                        	SinalistView.setVisibility(View.GONE);
//                        	SohulistView.setVisibility(View.VISIBLE);
//                    		SiteManager.getInstance().setContext(MainActivity.this);
//                    		SiteManager.getInstance(MainActivity.this).loadSites();
//                    		site = SiteManager.getInstance(MainActivity.this).getSites().get(currentSite);
//                    		site.addListener(MainActivity.this);
//                    		
//                    		if(SinaAccessToken==null){
//                    			site.logIn(sinauser);
//                    			SinaAccessToken=sinauser.getAccessToken();
//                    			SinaAccessSecret=sinauser.getAccessSecret();
//                    		}
//                    		else{
//                    			sinauser.setAccessToken(SinaAccessToken);
//                    			sinauser.setAccessSecret(SinaAccessSecret);
//                    			site.logIn(sinauser);
//                    		}
//                    		blogPage=0;
//                    		site.friendsTimeline(BLOGCOUNTPERPAGE,blogPage);
//                        }
//                });

		
//		findViewById(R.id.BtnSet).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intentSet = new Intent(MainActivity.this,
//						SetActivity.class);
//				intentSet.putExtra("site", currentSite);
//				MainActivity.this.startActivity(intentSet);
//			}
//		});
	}

	@Override
	public void onBeginRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String errorMessage) {
		Message msg = new Message();
		msg.what = SITEERROR;
		msg.obj = errorMessage;
		mainHandler.sendMessage(msg);
	}

	@Override
	public void onResponsed() {
		mainHandler.sendEmptyMessage(TESTOK);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AUTHREQUESTCODE) {
			if (resultCode == RESULT_OK) {
				if (data.hasExtra("user")) {
					switch(currentSite){
					case SiteManager.SINA:
						sinauser = (User) data.getSerializableExtra("user");
						site.logIn(sinauser);
						if (lastClickedView != null) {
							lastClickedView.performClick();
						}
						break;
					case SiteManager.SOHU:
						sohuuser = (User) data.getSerializableExtra("user");
						site.logIn(sohuuser);
						if (lastClickedView != null) {
							lastClickedView.performClick();
						}
						break;
					}

				}
			}
			Toast.makeText(this,
					resultCode == RESULT_OK ? "Auth Success" : "Auth Fail",
					Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
