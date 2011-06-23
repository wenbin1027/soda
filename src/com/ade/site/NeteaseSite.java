package com.ade.site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.json.JSONException;

import com.ade.parser.Parser;

public class NeteaseSite extends Site {

	@Override
	protected void onConstruct() {
		name="网易微博";
		rootUrl="http://api.t.163.com";
		appKey="KtbNU3B2tmfVwpWB";  
		appSecret="YEZc4DUdlPZVeDSpCEhYALXa9wd34VIM";
		oauthRequestUrl="http://api.t.163.com/oauth/request_token";
		oauthUrl="http://api.t.163.com/oauth/authenticate";
		oauthAccessUrl="http://api.t.163.com/oauth/access_token";
		oauthMethod=Site.OAUTH_GET;
		siteID=SiteManager.NETEASE;
	}

	@Override
	public void onError(String errorMessage, Parser parser) {
		notifyError(errorMessage);
	}

	@Override
	public void onResponsed(StatusLine statusLine, Header[] headers,
			HttpEntity entity, Parser parser) {
		if (statusLine.getStatusCode()==401){  //Unauthorized
			onError("用户未授权",parser);
			logOut();
			return;
		}
		if (parser!=null){
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8")); 
				StringBuilder builder = new StringBuilder(); 
				for (String line = null; (line = reader.readLine()) != null;) {
					builder.append(line).append("\n"); 
				} 
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
