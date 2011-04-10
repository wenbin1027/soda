package com.ade.site;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-ËÄÔÂ-2011 ÉÏÎç 08:33:52
 */
public class SiteManager {

	private static SiteManager instance;
	private List<Site> sites;



	public void finalize() throws Throwable {

	}

	private SiteManager(){

	}

	/**
	 * 
	 * @param site
	 */
	public void addSite(Site site){

	}

	public static SiteManager getInstance(){
		return null;
	}

	public List<Site> getSites(){
		return null;
	}

	public boolean loadSites(){
		return false;
	}

	private Site makeSite(){
		return null;
	}

	/**
	 * 
	 * @param site
	 */
	public void removeSite(Site site){

	}

	public void saveSites(){

	}

}