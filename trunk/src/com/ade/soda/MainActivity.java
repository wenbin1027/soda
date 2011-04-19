package com.ade.soda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;	
import android.widget.EditText;	
import android.widget.Toast;


public class MainActivity extends Activity implements SiteListener {
	private final String TAG="MainActivity";
	private final int AUTHREQUESTCODE=0;
	private final int SITEERROR=0;
	private final int TESTOK=1;
	private User user=new User();
	private Site site;
	private int currentSite=SiteManager.SOHU;  //测试时注意修改此处为要测的网站
	private View lastClickedView;
	
	private ResponseHandler<String> handler=new ResponseHandler<String>(){

		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpResponse res=response;
			return null;
		}
		
	};
	
	private Handler mainHandler=new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch(msg.what){
			case SITEERROR:
				String errorMessage=(String)msg.obj;
				Toast.makeText(MainActivity.this, errorMessage!=null?errorMessage:"未知错误", Toast.LENGTH_LONG).show();
				Intent intent=new Intent(MainActivity.this,OAuthActivity.class);
				intent.putExtra("site",currentSite);
				intent.putExtra("user",new User());
				startActivityForResult(intent,AUTHREQUESTCODE);
				break;
			case TESTOK:
				Toast.makeText(MainActivity.this, "测试成功", Toast.LENGTH_LONG).show();
				Log.i(TAG, "测试成功");
				break;
			}
			return false;
		}
	});
	
    @Override
    public void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);	
        
        SiteManager.getInstance().setContext(this);
        SiteManager.getInstance().loadSites();
        site=SiteManager.getInstance().getSites().get(currentSite);
		site.addListener(MainActivity.this);
        
        findViewById(R.id.BtnWrite).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				lastClickedView=v;
				//使用说明：通过如下代码可以进行发送微博的测试。首先会出现授权界面，授权后再点此按钮即可发送微博了。
				//site.updateText("微博测试vv");
				//若嫌授权麻烦，则可在第一次授权后在LogCat中找到TOKEN和SECRET的日志：
				//04-19 03:38:52.266: INFO/OAuthActivity(1841): TOKEN=46b571ca341cfeb4737f419ed4ce0392  SECRET=920143c011048ab9e4c8904440e7ed1a
				//然后使用如下代码.
				//注意这里的TOKEN和SECRET对不同的用户名是不一样的，请仔细使用
				user.setAccessToken("46b571ca341cfeb4737f419ed4ce0392");  
				user.setAccessSecret("920143c011048ab9e4c8904440e7ed1a");
				site.logIn(user);
				site.friendsTimeline();
				//site.updateText("上传微博");
				//site.uploadImage("/data/data/com.ade.soda/10697.jpg","微博测试vv");
				//注意要先将欲上传的图片PUSH到模拟器对应的路径下
			}
        });
        
        findViewById(R.id.BtnRefresh).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

			}
        });
     }

	@Override
	public void onBeginRequest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String errorMessage) {
		Message msg=new Message();
		msg.what=SITEERROR;
		msg.obj=errorMessage;
		mainHandler.sendMessage(msg);
	}


	@Override
	public void onResponsed() {
		mainHandler.sendEmptyMessage(TESTOK);
	}    

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==AUTHREQUESTCODE){
			if (resultCode==RESULT_OK){
				if (data.hasExtra("user")){
					user=(User)data.getSerializableExtra("user");
					site.logIn(user);
					if (lastClickedView!=null){
						lastClickedView.performClick();
					}
				}
			}
			Toast.makeText(this, resultCode==RESULT_OK?"Auth Success":"Auth Fail", Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}