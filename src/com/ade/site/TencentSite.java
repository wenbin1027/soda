package com.ade.site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.json.JSONException;

import com.ade.parser.Parser;

public class TencentSite extends Site {

	@Override
	protected void onConstruct() {
		name="腾讯微博";
		rootUrl="https://open.t.qq.com";
		appKey="1754dad6631144c293bbe8ebe95333cd";  
		appSecret="c1f57e49d92eb25b9fb3d8e46437773a";
		oauthRequestUrl="/cgi-bin/request_token";
		oauthUrl="/cgi-bin/authorize";
		oauthAccessUrl="/cgi-bin/access_token";
		oauthMethod=Site.OAUTH_GET;
		siteID=SiteManager.TENCENT;
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
