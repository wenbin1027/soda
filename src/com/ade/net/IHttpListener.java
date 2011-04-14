package com.ade.net;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:34:15
 */
public interface IHttpListener {

	public void onBeginRequest();

	public void onError(String errorMessage);

	public void onResponsed(StatusLine statusLine,Header[] headers,HttpEntity entity);

}