package com.ade.parser;

import org.json.JSONException;
import com.ade.site.Site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:34:38
 */
public abstract class Parser {

	public Parser(){

	}

	/**
	 * 
	 * @param stream
	 */
	public int getErrorCode(){
		return 0;
	}

	/**
	 * 
	 * @param stream
	 * @param site
	 * @throws JSONException 
	 */
	protected abstract boolean onParse(String in, Site site) throws JSONException;

	/**
	 * 
	 * @param stream
	 * @param site
	 * @throws JSONException 
	 */
	public boolean parse(String in, Site site) throws JSONException{
		return onParse(in,site);
	}

}