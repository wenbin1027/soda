package com.ade.restapi;

import com.ade.parser.Parser;
import com.ade.site.Site;

public class TencentAccountVerify extends AccountVerifyInterface {
	private static final String PATH="/api/user/info?format=json";

	public TencentAccountVerify(Parser parser) {
		super(parser);
	}

	@Override
	protected String getUrl(Site site) {
		return new String(site.getRootUrl()+PATH);
	}

}
