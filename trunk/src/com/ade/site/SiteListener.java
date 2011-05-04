package com.ade.site;


public interface SiteListener{

	public void onBeginRequest();

	public void onError(String errorMessage);

	public void onResponsed();

}