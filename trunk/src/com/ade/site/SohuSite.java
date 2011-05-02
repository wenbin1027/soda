package com.ade.site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.json.JSONException;

import android.util.Log;

import com.ade.parser.Parser;

public class SohuSite extends Site {

	protected void onConstruct(){
		name="搜狐微博";
		rootUrl="http://api.t.sohu.com";
		appKey="bHi9G9CcHCeXBMhWReow";  
		appSecret="ITDdmLZCeALHO847#DWlRn2Vl-z2lcU%U!RO4DIo";
		oauthRequestUrl="/oauth/request_token";
		oauthUrl="/oauth/authorize";
		oauthAccessUrl="/oauth/access_token";
		siteID=SiteManager.SOHU;
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