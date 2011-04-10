package com.ade.net;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:34:15
 */
public interface IHttpListener {

	public void onBeginRequest();

	public void onError();

	public void onResponsed();

}