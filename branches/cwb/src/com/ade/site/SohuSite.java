package com.ade.site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.json.JSONException;
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
		initFaceMap();
	}

	private void initFaceMap(){
		faceMap.put("[微笑]", "hehe");
		faceMap.put("[色]", "huaxin");	
		faceMap.put("[呲牙]", "xixi");
		faceMap.put("[偷笑]", "touxiao");
		faceMap.put("[害羞]", "haixiu");
		
		faceMap.put("[大哭]", "zhuakuang");
		faceMap.put("[哭]", "lei");
		faceMap.put("[酷]", "ku");
		faceMap.put("[发火]", "numa");
		faceMap.put("[怒]", "nu");
		
		faceMap.put("[调皮]", "zuoguilian");
		faceMap.put("[睡觉]", "shuijiao");
		faceMap.put("[困]", "dahaqi");
		faceMap.put("[汗]", "han");
		faceMap.put("[嘘]", "xu");
		
		faceMap.put("[吐]", "tu");
		faceMap.put("[馋]", "chanzui");
		faceMap.put("[鄙视]", "bishi");
		faceMap.put("[讽刺]", "heng");
		faceMap.put("[晕]", "yun");
		
		faceMap.put("[衰]", "shuai");
		faceMap.put("[闭嘴]", "bizui");
		faceMap.put("[握手]", "woshou");
		faceMap.put("[猪]", "zhutou");
		faceMap.put("[顶]", "zan");
		
		faceMap.put("[胜利]", "ye");
		faceMap.put("[蛋糕]", "dangao");
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