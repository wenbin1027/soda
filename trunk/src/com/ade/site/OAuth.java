package com.ade.site;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.ade.net.HttpNet;
import com.ade.net.IHttpListener;
import com.ade.util.OAuthUtil;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.*;

public class OAuth implements IHttpListener{
	private static final String TAG="OAuthActivity";

 	private static final String CALLBACKURL="http://auth";
 	private static final int REQUESTTOKEN=0;  //获取请求令牌环节
 	private static final int ACCESSTOKEN=1;   //获取访问令牌环节
    
 	private String requestUrl="";
 	private String authorizeUrl="";
 	private String accessUrl="";
 	private String consumerKey="";
 	private String consumerSecret="";
 	private String oauth_token_secret="";  //用户同意授权时的临时的密钥
 	private boolean isSuccess=false;
 	private WebView webView;
 	private AccessToken accessToken;       //最终的经授权的token
 	private OAuthListener listener;
 	private HttpNet httpnet=new HttpNet(this);
 	private int currentStep=REQUESTTOKEN;  //当前进行到哪一个环节

 	//用户授权后的处理，用于获取accessToken
	private Handler authorizeHandler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.obj!=null){
				AccessToken token=(AccessToken)msg.obj;
				HttpUriRequest request=new HttpPost(accessUrl);
				
		        SortedSet<String> params=new TreeSet<String>();
		        String nonce=OAuthUtil.getNonce();
		        String timestamp=""+(new Date()).getTime()/1000;
		        params.add(OAuthUtil.OAUTH_NONCE+OAuthUtil.LINK+nonce);
		        params.add(OAuthUtil.OAUTH_SIGNATURE_METHOD+OAuthUtil.LINK+OAuthUtil.HMACSHA1);
		        params.add(OAuthUtil.OAUTH_TIMESTAMP+OAuthUtil.LINK+timestamp);
		        params.add(OAuthUtil.OAUTH_CONSUMER_KEY+OAuthUtil.LINK+consumerKey);
		        params.add(OAuthUtil.OAUTH_VERSION+OAuthUtil.LINK+OAuthUtil.VERSION10);
		        params.add(OAuthUtil.OAUTH_TOKEN+OAuthUtil.LINK+token.token);
		        params.add(OAuthUtil.OAUTH_VERIFIER+OAuthUtil.LINK+token.secret);

		        String baseString = OAuthUtil.makeBaseString(params,request.getMethod(),accessUrl);
		        String signature=OAuthUtil.makeSignature(baseString,URLEncoder.encode(consumerSecret)+'&'+oauth_token_secret);
		        
		        String separater="\", ";
		        StringBuilder header=new StringBuilder(OAuthUtil.OAUTH);
		        header.append(OAuthUtil.OAUTH_NONCE+OAuthUtil.LINK+"\"");
		        header.append(nonce+separater);
		        header.append(OAuthUtil.OAUTH_SIGNATURE_METHOD+OAuthUtil.LINK+"\"");
		        header.append(OAuthUtil.HMACSHA1+separater);
		        header.append(OAuthUtil.OAUTH_TIMESTAMP+OAuthUtil.LINK+"\"");
		        header.append(timestamp+separater);
		        header.append(OAuthUtil.OAUTH_CONSUMER_KEY+OAuthUtil.LINK+"\"");
		        header.append(consumerKey+separater);
		        header.append(OAuthUtil.OAUTH_VERSION+OAuthUtil.LINK+"\"");
		        header.append(OAuthUtil.VERSION10+separater);
		        header.append(OAuthUtil.OAUTH_TOKEN+OAuthUtil.LINK+"\"");
		        header.append(token.token+separater);
		        header.append(OAuthUtil.OAUTH_VERIFIER+OAuthUtil.LINK+"\"");
		        header.append(token.secret+separater);
		        header.append(OAuthUtil.OAUTH_SIGNATURE+OAuthUtil.LINK+"\"");
		        header.append(URLEncoder.encode(signature));
		        header.append("\"");
		        
		        currentStep=ACCESSTOKEN;
		        
		        request.addHeader(OAuthUtil.AUTHORIZATION, header.toString());
		        httpnet.request(request);	
		        Log.i("OAuth", "AccessToken");
			}
			return false;
		}
	});
 	
	public OAuth(String requestUrl,String authorizeUrl,String accessUrl,WebView webView){
		this.requestUrl=requestUrl;
		this.authorizeUrl=authorizeUrl;
		this.accessUrl=accessUrl;
		this.webView=webView;
		initWebView();
	}
	
	//请求访问token
	public void requestAccessToken(String consumerKey,String consumerSecret){
		this.consumerKey=consumerKey;
		this.consumerSecret=consumerSecret;
		
		HttpUriRequest request=new HttpPost(requestUrl);
		
        SortedSet<String> params=new TreeSet<String>();
        String nonce=OAuthUtil.getNonce();
        String timestamp=""+(new Date()).getTime()/1000;
        params.add(OAuthUtil.OAUTH_NONCE+OAuthUtil.LINK+nonce);
		params.add(OAuthUtil.OAUTH_CALLBACK+OAuthUtil.LINK+URLEncoder.encode(CALLBACKURL));
        params.add(OAuthUtil.OAUTH_SIGNATURE_METHOD+OAuthUtil.LINK+OAuthUtil.HMACSHA1);
        params.add(OAuthUtil.OAUTH_TIMESTAMP+OAuthUtil.LINK+timestamp);
        params.add(OAuthUtil.OAUTH_CONSUMER_KEY+OAuthUtil.LINK+consumerKey);
        params.add(OAuthUtil.OAUTH_VERSION+OAuthUtil.LINK+OAuthUtil.VERSION10);

        String baseString = OAuthUtil.makeBaseString(params,request.getMethod(),requestUrl);
        String signature=OAuthUtil.makeSignature(baseString,URLEncoder.encode(consumerSecret)+'&');
        
        String separater="\", ";
        StringBuilder header=new StringBuilder(OAuthUtil.OAUTH);
        header.append(OAuthUtil.OAUTH_NONCE+OAuthUtil.LINK+"\"");
        header.append(nonce+separater);
        header.append(OAuthUtil.OAUTH_CALLBACK+OAuthUtil.LINK+"\"");
		header.append(URLEncoder.encode(CALLBACKURL)+separater);
        header.append(OAuthUtil.OAUTH_SIGNATURE_METHOD+OAuthUtil.LINK+"\"");
        header.append(OAuthUtil.HMACSHA1+separater);
        header.append(OAuthUtil.OAUTH_TIMESTAMP+OAuthUtil.LINK+"\"");
        header.append(timestamp+separater);
        header.append(OAuthUtil.OAUTH_CONSUMER_KEY+OAuthUtil.LINK+"\"");
        header.append(consumerKey+separater);
        header.append(OAuthUtil.OAUTH_VERSION+OAuthUtil.LINK+"\"");
        header.append(OAuthUtil.VERSION10+separater);
        header.append(OAuthUtil.OAUTH_SIGNATURE+OAuthUtil.LINK+"\"");
        header.append(URLEncoder.encode(signature));
        header.append("\"");
        
        currentStep=REQUESTTOKEN;
        
        request.addHeader(OAuthUtil.AUTHORIZATION, header.toString());
        httpnet.request(request);	
	}
 	
 	/**
	 * @return the accessToken
	 */
	public AccessToken getAccessToken() {
		return accessToken;
	}
	
	public boolean isSuccess(){
		return isSuccess;
	}

	/**
	 * 
	 */
	private void initWebView() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
			String url="";
//			public boolean shouldOverrideUrlLoading (WebView view, String url){
//				if (url.startsWith(CALLBACKURL)){
//					return true;
//				}
//				return false;
//			}

			public void onPageStarted (WebView view, String url, Bitmap favicon){
				if (!this.url.equals(url) && url.startsWith(CALLBACKURL)){
					this.url=url;
					view.stopLoading();
					saveAccessToken(url);  //对用户同意授权的回调页面进行处理
				}				
			}
			/**分离用户授权时的临时令牌和密钥
			 * @param url
			 */
			private void saveAccessToken(String url) {
				Uri uri = Uri.parse(url);
				String oauth_token = uri.getQueryParameter(OAuthUtil.OAUTH_TOKEN);
				String oauth_verifier = uri.getQueryParameter(OAuthUtil.OAUTH_VERIFIER);

				AccessToken authorizeToken=new AccessToken();
				authorizeToken.token=oauth_token;
				authorizeToken.secret=oauth_verifier;
				
				Message msg=new Message();
				msg.obj=authorizeToken;
				authorizeHandler.sendMessage(msg);
				uri=null;
			}
		});
		webView.setWebChromeClient(new WebChromeClient(){
			public void onCloseWindow (WebView window){
				notifyListener();  //用户取消授权时通知调用方
			}
		});
	}

	/**
	 * 
	 */
	private void notifyListener() {
		if (listener!=null){
			listener.onFinish(this);
		}
	}

 	/**
	 * @param listener the listener to set
	 */
	public void setListener(OAuthListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onBeginRequest() {
		
	}

	@Override
	public void onError(String errorMessage) {
		notifyListener();
	}

	@Override
	public void onResponsed(StatusLine statusLine, Header[] headers,
			HttpEntity entity) {
		InputStream content=null;
		try {
			content = entity.getContent();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (content!=null){
			InputStreamReader reader=new InputStreamReader(content);
			char[] buf=new char[(int) entity.getContentLength()];
			try {
				reader.read(buf);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				notifyListener();
				return;
			}
			String str=new String(buf);
//			Log.i(TAG, "Auth="+str);
			if (currentStep==REQUESTTOKEN){
				parseRequestToken(str);
			}
			else if (currentStep==ACCESSTOKEN){
				parseAccessToken(str);
			}
			buf=null;
		}
		else{
			notifyListener();
		}
	}

	/**
	 * @param str
	 */
	private void parseAccessToken(String str) {
		String substr[]=str.split("&");
		String token="";
		String secret="";
		for(int i=0;i<substr.length;i++){
			String item[]=substr[i].split("=");
			if (item[0].equalsIgnoreCase("oauth_token")){
				token=item[1];
			}
			if (item[0].equalsIgnoreCase("oauth_token_secret")){
				secret=item[1];
			}
			item=null;
		}
		accessToken=new AccessToken();
		accessToken.token=token;
		accessToken.secret=secret;
		Log.i(TAG,"TOKEN="+token+"  SECRET="+secret);
		isSuccess=true;
		substr=null;
		notifyListener();
	}

	/**
	 * @param str
	 */
	private void parseRequestToken(String str) {
		String substr[]=str.split("&");
		String token="";
		for(int i=0;i<substr.length;i++){
			String item[]=substr[i].split("=");
			if (item[0].equalsIgnoreCase("oauth_token")){
				token=item[1];
			}
			if (item[0].equalsIgnoreCase("oauth_token_secret")){
				oauth_token_secret=item[1];
			}
			item=null;
		}
		substr=null;
		StringBuilder url=new StringBuilder(authorizeUrl);
		url.append("?");
		url.append("oauth_token=");
		url.append(token);
		url.append("&oauth_callback=");
		url.append(URLEncoder.encode(CALLBACKURL));
		webView.loadUrl(url.toString());
	}

}
