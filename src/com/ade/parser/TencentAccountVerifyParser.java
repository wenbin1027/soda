package com.ade.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ade.site.Site;
import com.ade.site.User;

public class TencentAccountVerifyParser extends TencentBasicParser {

	@Override
	protected boolean onParse(String in, Site site) throws JSONException {
		JSONObject ret=new JSONObject(in);
		JSONObject retUser=ret.getJSONObject("data");
		User myRetUser=site.getLoggedInUser();
		return parseUser(retUser,myRetUser);
	}

}
