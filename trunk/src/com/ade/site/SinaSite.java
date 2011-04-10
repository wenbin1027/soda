package com.ade.site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-四月-2011 上午 08:33:50
 */
public class SinaSite extends Site {

	public SinaSite(){
		name="新浪";
		rootUrl="http://api.t.sina.com.cn";
		appKey="3393006127";  //TODO 临时测试用
		appSecret="70768c222a4613ed7f930bae3dee2e57";
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	protected void onConstruct(){

	}

}