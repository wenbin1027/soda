package com.ade.parser;

import java.io.InputStream;

import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:34:37
 */
public class FriendsTimelineParser extends Parser {

	public FriendsTimelineParser(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param stream
	 * @param site
	 */
	protected boolean onParse(InputStream stream, Site site){
		return false;
	}

}