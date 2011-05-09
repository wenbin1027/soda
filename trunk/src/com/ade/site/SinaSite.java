package com.ade.site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.json.JSONException;

import com.ade.parser.Parser;

public class SinaSite extends Site {

	protected void onConstruct(){
		name="新浪微博";
		rootUrl="http://api.t.sina.com.cn";
		appKey="2321851444";  
		appSecret="786fd8cb4d3f237599b5d587b6f6c0e9";
		oauthRequestUrl="/oauth/request_token";
		oauthUrl="/oauth/authorize";
		oauthAccessUrl="/oauth/access_token";
		siteID=SiteManager.SINA;
		initFaceMap();
	}

	private void initFaceMap(){
		faceMap.put("[呵呵]", "hehe");
		faceMap.put("[嘻嘻]", "xixi");
		faceMap.put("[哈哈]", "haha");
		faceMap.put("[爱你]", "aini");
		faceMap.put("[晕]", "yun");
		
		faceMap.put("[泪]", "lei");
		faceMap.put("[馋嘴]", "chanzui");
		faceMap.put("[抓狂]", "zhuakuang");
		faceMap.put("[哼]", "heng");
		faceMap.put("[可爱]", "keai");
		
		faceMap.put("[怒]", "nu");
		faceMap.put("[汗]", "han");
		faceMap.put("[害羞]", "haixiu");
		faceMap.put("[睡觉]", "shuijiao");
		faceMap.put("[钱]", "qian");
		
		faceMap.put("[偷笑]", "touxiao");
		faceMap.put("[酷]", "ku");
		faceMap.put("[衰]", "shuai");
		faceMap.put("[吃惊]", "chijing");
		faceMap.put("[闭嘴]", "bizui");
		
		faceMap.put("[鄙视]", "bishi");
		faceMap.put("[挖鼻屎]", "wabishi");
		faceMap.put("[花心]", "huaxin");	
		faceMap.put("[鼓掌]", "guzhang");
		faceMap.put("[失望]", "shiwang");
		
		faceMap.put("[思考]", "sikao");
		faceMap.put("[生病]", "shengbing");
		faceMap.put("[亲亲]", "qinqin");
		faceMap.put("[怒骂]", "numa");
		faceMap.put("[太开心]", "taikaixin");
		
		faceMap.put("[懒得理你]", "landelini");	
		faceMap.put("[右哼哼]", "youhengheng");
		faceMap.put("[左哼哼]", "zuohengheng");
		faceMap.put("[嘘]", "xu");
		faceMap.put("[委屈]", "weiqu");
		
		faceMap.put("[吐]", "tu");
		faceMap.put("[可怜]", "kelian");
		faceMap.put("[打哈气]", "dahaqi");
		faceMap.put("[做鬼脸]", "zuoguilian");
		faceMap.put("[握手]", "woshou");
		
		faceMap.put("[耶]", "ye");
		faceMap.put("[gook]", "good");
		faceMap.put("[弱]", "ruo");		
		faceMap.put("[不要]", "buyao");
		faceMap.put("[ok]", "ok");
		
		faceMap.put("[赞]", "zan");
		faceMap.put("[来]", "lai");
		faceMap.put("[蛋糕]", "dangao");
		faceMap.put("[心]", "xin");		
		faceMap.put("[伤心]", "shangxin");
		
		faceMap.put("[猪头]", "zhutou");
		faceMap.put("[威武]", "v5");
		faceMap.put("[给力]", "geili");
		faceMap.put("[神马]", "shenma");		
		faceMap.put("[浮云]", "fuyun");
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