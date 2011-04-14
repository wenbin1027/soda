package com.ade.util;

import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

public class OAuthUtil {
	public static final String OAUTH_TOKEN="oauth_token";
	public static final String OAUTH_VERIFIER="oauth_verifier";
	public static final String OAUTH_NONCE="oauth_nonce";
	public static final String OAUTH_CALLBACK="oauth_callback";
	public static final String OAUTH_SIGNATURE_METHOD="oauth_signature_method";
	public static final String OAUTH_TIMESTAMP="oauth_timestamp";
	public static final String OAUTH_CONSUMER_KEY="oauth_consumer_key";
	public static final String OAUTH_VERSION="oauth_version";
	public static final String OAUTH_SIGNATURE="oauth_signature";
	public static final String HMACSHA1="HMAC-SHA1";
	public static final String VERSION10="1.0";
	public static final char	LINK='=';
 	
 	public static void signRequest(String url,HttpUriRequest request,List<NameValuePair> data,
			String consumerKey,String consumerSecret,String accessToken,String accessSecret){
        SortedSet<String> params=new TreeSet<String>();
        String nonce=getNonce();
        String timestamp=""+(new Date()).getTime()/1000;
        params.add(OAUTH_NONCE+LINK+nonce);
        params.add(OAUTH_SIGNATURE_METHOD+LINK+HMACSHA1);
        params.add(OAUTH_TIMESTAMP+LINK+timestamp);
        params.add(OAUTH_CONSUMER_KEY+LINK+consumerKey);
        params.add(OAUTH_VERSION+LINK+VERSION10);
        params.add(OAUTH_TOKEN+LINK+accessToken);
        if (data!=null){
	        for(int i=0;i<data.size();i++){
	        	params.add(data.get(i).getName()+LINK+URLEncoder.encode(data.get(i).getValue()));
	        }
        }

        String baseString = makeBaseString(params,request.getMethod(),url);
        String signature=makeSignature(baseString,consumerSecret+'&'+accessSecret);
        
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
        header.append(accessToken+separater);
        header.append(OAUTH_SIGNATURE+LINK+"\"");
        header.append(URLEncoder.encode(signature));
        header.append("\"");
        
        request.addHeader("authorization", header.toString());
	}
 	
	//生成无用的随机字符串
	public static String getNonce() {
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
	public static String makeSignature(String data, String key) {
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
	
	/**
	 * @param params
	 * @return
	 */
	public static String makeBaseString(SortedSet<String> params,String httpMethod,String url) {
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
}
