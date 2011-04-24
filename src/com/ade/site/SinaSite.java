package com.ade.site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.json.JSONException;

import com.ade.parser.Parser;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:50
 */
public class SinaSite extends Site {

	protected void onConstruct(){
		name="新浪微博";
		rootUrl="http://api.t.sina.com.cn";
		appKey="2321851444";  
		appSecret="786fd8cb4d3f237599b5d587b6f6c0e9";
		oauthRequestUrl="/oauth/request_token";
		oauthUrl="/oauth/authorize";
		oauthAccessUrl="/oauth/access_token";
	}

	@Override
	public void onError(String errorMessage,Parser parser) {
		notifyError(errorMessage);
	}

	@Override
	public void onResponsed(StatusLine statusLine, Header[] headers,
			HttpEntity entity,Parser parser) {
		
		if (statusLine.getStatusCode()==401){  //Unauthorized
			onError("用户未授权",parser);
			return;
		}
		isLoggedIn=true;
		
		if (parser!=null){
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8")); 
				StringBuilder builder = new StringBuilder(); 
				for (String line = null; (line = reader.readLine()) != null;) {
					builder.append(line).append("\n"); 
				} 
				entity.consumeContent();
				parser.parse(builder.toString(), this);
				builder=null;
				reader.close();
			} catch (IllegalStateException e) {
				e.printStackTrace();
				onError("数据解析出错",parser);
				return;				
			} catch (JSONException e) {
				e.printStackTrace();
				onError("数据解析出错",parser);
				return;				
			} catch (IOException e) {
				e.printStackTrace();
				onError("数据解析出错",parser);
				return;				
			}
		}
		
		notifyResponse();
	}

}