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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;	
import android.widget.EditText;	


public class MainActivity extends Activity {
	private ResponseHandler<String> handler=new ResponseHandler<String>(){

		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpResponse res=response;
			return null;
		}
		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);	
        findViewById(R.id.BtnWrite).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//for test
				Site site=new SohuSite();
				UpdateInterface update=new SohuUpdate();
				site.setUpdateInterface(update);
				User user=new User();
				user.setAccessToken("afcEmgzaB3SxsWAdCosr");
				user.setAccessSecret("KY6Q9LHhwkgKAZbfRfCSw$$ZOM%!STwQ2YAro)(i");
				site.logIn(user);
				site.updateText("[色]微博测试");
			}
        });
        
        findViewById(R.id.BtnRefresh).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				startActivity(
//						new Intent(MainActivity.this,OAuthActivity.class));
				testSohuUpdate();
			}

			/**仅供测试
			 * 
			 */
			private void testSinaUpdate() {
				String url="http://api.t.sina.com.cn/statuses/update.json";
				String text="程序测试abc";
				text=URLEncoder.encode(text);
				
				HttpPost request=new HttpPost(url);
				request.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
						false);  //消除握手
				
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("status",text));
				try {
					StringEntity entity=new UrlEncodedFormEntity(params);
					request.setEntity(entity);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
				OAuthUtil.signRequest(url, request, params, 
						"3393006127","70768c222a4613ed7f930bae3dee2e57", 
						"ced38c8cf187af214d858f07cbd4cb88", "fc94636e3d4d5490a4ed142c5e3cbd5b");
				HttpClient client=new DefaultHttpClient();
				try {
					client.execute(request,handler);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**仅供测试
			 * 
			 */
			private void testSohuUpdate() {
				String url="http://api.t.sohu.com/statuses/update.json";
				String text="唉，搜狐呀...";
				text=URLEncoder.encode(text);
				
				HttpPost request=new HttpPost(url);
				request.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
						false);  //消除握手
				
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("status",text));
				try {
					StringEntity entity=new UrlEncodedFormEntity(params);
					request.setEntity(entity);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
				OAuthUtil.signRequest(url, request, params, 
						"afcEmgzaB3SxsWAdCosr","KY6Q9LHhwkgKAZbfRfCSw$$ZOM%!STwQ2YAro)(i", 
						"514bc999f0457b7ec6fff91c6bf38302", "b11b5af823c4d0e62011655f2f295b22");
				HttpClient client=new DefaultHttpClient();
				try {
					client.execute(request,handler);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        });
     }    
}