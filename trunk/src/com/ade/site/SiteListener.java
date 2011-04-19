package com.ade.site;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:51
 */
public interface SiteListener {

	public void onBeginRequest();

	public void onError(String errorMessage);

	public void onResponsed();

}