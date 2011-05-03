package com.ade.restapi;

import com.ade.parser.Parser;
import com.ade.site.Site;

public class SohuAccountVerify extends AccountVerifyInterface {
	private static final String PATH="/account/verify_credentials.json";
	
	public SohuAccountVerify(Parser parser) {
		super(parser);
	}

	@Override
	protected String getUrl(Site site) {
		return new String(site.getRootUrl()+PATH);
	}

}
