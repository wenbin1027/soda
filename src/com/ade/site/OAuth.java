package com.ade.site;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ade.util.Base64;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OAuth {
	private static final String TAG="OAuthActivity";
	private static final String OAUTH_TOKEN="oauth_token";
	private static final String OAUTH_VERIFIER="oauth_verifier";
	private static final String OAUTH_NONCE="oauth_nonce";
	private static final String OAUTH_CALLBACK="oauth_callback";
	private static final String OAUTH_SIGNATURE_METHOD="oauth_signature_method";
 	private static final String OAUTH_TIMESTAMP="oauth_timestamp";
 	private static final String OAUTH_CONSUMER_KEY="oauth_consumer_key";
 	private static final String OAUTH_VERSION="oauth_version";
 	private static final String OAUTH_SIGNATURE="oauth_signature";
 	private static final String HMACSHA1="HMAC-SHA1";
 	private static final String VERSION10="1.0";
 	private static final char	LINK='=';
 	private static final String CALLBACKURL="http://auth";
    
 	private String requestUrl="";
 	private String authorizeUrl="";
 	private String accessUrl="";
 	private String consumerKey="";
 	private String consumerSecret="";
 	private String oauth_token_secret="";  //用户同意授权时的临时的
 	private boolean isSuccess=false;
 	private WebView webView;
 	private AccessToken accessToken;       //最终的经授权的token
 	private OAuthListener listener;

 	//用户授权后的处理，用于获取accessToken
	private Handler authorizeHandler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.obj!=null){
				AccessToken token=(AccessToken)msg.obj;
				
		        SortedSet<String> params=new TreeSet<String>();
		        String nonce=getNonce();
		        String timestamp=""+(new Date()).getTime()/1000;
		        params.add(OAUTH_NONCE+LINK+nonce);
		        params.add(OAUTH_SIGNATURE_METHOD+LINK+HMACSHA1);
		        params.add(OAUTH_TIMESTAMP+LINK+timestamp);
		        params.add(OAUTH_CONSUMER_KEY+LINK+consumerKey);
		        params.add(OAUTH_VERSION+LINK+VERSION10);
		        params.add(OAUTH_TOKEN+LINK+token.token);
		        params.add(OAUTH_VERIFIER+LINK+token.secret);

		        String baseString = makeBaseString(params,"POST",accessUrl);
		        String signature=sign(baseString,consumerSecret+'&'+oauth_token_secret);
		        
		        String separater="\", ";
		        StringBuilder header=new StringBuilder("OAuth ");
		        header.append(OAUTH_NONCE+LINK+"\"");
		        header.append(nonce+separater);
		        header.append(OAUTH_SIGNATURE_METHOD+LINK+"\"");
		        header.append(HMACSHA1+separater);
		        header.append(OAUTH_TIMESTAMP+LINK+"\"");
		        header.append(timestamp+separater);
		        header.append(OAUTH_CONSUMER_KEY+LINK+"\"");
		        header.append(consumerKey+separater);
		        header.append(OAUTH_VERSION+LINK+"\"");
		        header.append(VERSION10+separater);
		        header.append(OAUTH_TOKEN+LINK+"\"");
		        header.append(token.token+separater);
		        header.append(OAUTH_VERIFIER+LINK+"\"");
		        header.append(token.secret+separater);
		        header.append(OAUTH_SIGNATURE+LINK+"\"");
		        header.append(URLEncoder.encode(signature));
		        header.append("\"");
		        
				HttpPost post=new HttpPost(accessUrl);
		        post.addHeader("authorization", header.toString());
		        webRequest(post,accessHandler);						
			}
			return false;
		}
	});
 	
	//获取accessToken时的网络响应，分享出最终的访问token
	private ResponseHandler<String> accessHandler=new ResponseHandler<String>(){
		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			InputStream content=response.getEntity().getContent();
			InputStreamReader reader=new InputStreamReader(content);
			char[] buf=new char[(int) response.getEntity().getContentLength()];
			reader.read(buf);
			String str=new String(buf);
//			Log.i(TAG, "Auth="+str);
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
			buf=null;
			notifyListener();
			return null;
		}
	};
	
	//请求授权的网络响应处理，导向到浏览器的授权页面
	private ResponseHandler<String> requestHandler=new ResponseHandler<String>(){
		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			InputStream content=response.getEntity().getContent();
			InputStreamReader reader=new InputStreamReader(content);
			char[] buf=new char[(int) response.getEntity().getContentLength()];
			reader.read(buf);
			String str=new String(buf);
//			Log.i(TAG, "Auth="+str);
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
			buf=null;
			StringBuilder url=new StringBuilder(authorizeUrl);
			url.append("?");
			url.append("oauth_token=");
			url.append(token);
			url.append("&oauth_callback=");
			url.append(URLEncoder.encode(CALLBACKURL));
			webView.loadUrl(url.toString());
			return null;
		}
	};
	
	public OAuth(String requestUrl,String authorizeUrl,String accessUrl,WebView webView){
		this.requestUrl=requestUrl;
		this.authorizeUrl=authorizeUrl;
		this.accessUrl=accessUrl;
		this.webView=webView;
		initWebView();
	}
	
	//生成无用的随机字符串
	private String getNonce() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 18; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	//使用HMAC-SHA1进行签名
	private String sign(String data, String key) {
		String macName="HmacSHA1";
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance(macName);
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), macName);
			mac.init(spec);
			byteHMAC = mac.doFinal(data.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ignore) {
		}
		String oauth = Base64.encodeToString(byteHMAC,Base64.NO_WRAP);
		return oauth;
	}
	
	//请求访问token
	public void requestAccessToken(String consumerKey,String consumerSecret){
		this.consumerKey=consumerKey;
		this.consumerSecret=consumerSecret;
		
        SortedSet<String> params=new TreeSet<String>();
        String nonce=getNonce();
        String timestamp=""+(new Date()).getTime()/1000;
        params.add(OAUTH_NONCE+LINK+nonce);
		params.add(OAUTH_CALLBACK+LINK+URLEncoder.encode(CALLBACKURL));
        params.add(OAUTH_SIGNATURE_METHOD+LINK+HMACSHA1);
        params.add(OAUTH_TIMESTAMP+LINK+timestamp);
        params.add(OAUTH_CONSUMER_KEY+LINK+consumerKey);
        params.add(OAUTH_VERSION+LINK+VERSION10);

        String baseString = makeBaseString(params,"POST",requestUrl);
        String signature=sign(baseString,consumerSecret+'&');
        
        String separater="\", ";
        StringBuilder header=new StringBuilder("OAuth ");
        header.append(OAUTH_NONCE+LINK+"\"");
        header.append(nonce+separater);
        header.append(OAUTH_CALLBACK+LINK+"\"");
		header.append(URLEncoder.encode(CALLBACKURL)+separater);
        header.append(OAUTH_SIGNATURE_METHOD+LINK+"\"");
        header.append(HMACSHA1+separater);
        header.append(OAUTH_TIMESTAMP+LINK+"\"");
        header.append(timestamp+separater);
        header.append(OAUTH_CONSUMER_KEY+LINK+"\"");
        header.append(consumerKey+separater);
        header.append(OAUTH_VERSION+LINK+"\"");
        header.append(VERSION10+separater);
        header.append(OAUTH_SIGNATURE+LINK+"\"");
        header.append(URLEncoder.encode(signature));
        header.append("\"");
        
		HttpPost post=new HttpPost(requestUrl);
        post.addHeader("authorization", header.toString());
        webRequest(post,requestHandler);		
	}

	/**发起网络请求
	 * @param header
	 */
	private void webRequest(HttpUriRequest request,ResponseHandler<String> handler) {
        HttpClient client=new DefaultHttpClient();
        try {
			client.execute(request, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		webView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading (WebView view, String url){
				if (url.startsWith(CALLBACKURL)){
					saveAccessToken(url);  //对用户同意授权的回调页面进行处理
					return true;
				}
				return false;
			}

			/**分离用户授权时的临时令牌和密钥
			 * @param url
			 */
			private void saveAccessToken(String url) {
				Uri uri = Uri.parse(url);
				String oauth_token = uri.getQueryParameter(OAUTH_TOKEN);
				String oauth_verifier = uri.getQueryParameter(OAUTH_VERIFIER);

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
	 * @param params
	 * @return
	 */
	private String makeBaseString(SortedSet<String> params,String httpMethod,String url) {
		StringBuilder baseString=new StringBuilder(httpMethod);
		baseString.append('&');
		baseString.append(URLEncoder.encode(url));
		baseString.append('&');
		
		StringBuilder tempString=new StringBuilder("");
		for(Iterator<String> iterator=params.iterator();iterator.hasNext();){
			tempString.append(iterator.next());
			tempString.append('&');
		}
		tempString.deleteCharAt(tempString.length()-1);
		
		baseString.append(URLEncoder.encode(tempString.toString()));
		return baseString.toString();
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
	
	public static String signRequest(String url,String httpMethod,Header[] headers,byte[] postData,
			String consumerKey,String consumerSecret,String accessSecret){
		return "";
	}

}
