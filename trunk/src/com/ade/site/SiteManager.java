package com.ade.site;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ade.parser.FriendsTimelineParser;
import com.ade.restapi.SohuFriendsTimeline;
import com.ade.restapi.SohuUpdate;
import com.ade.restapi.SohuUpload;
import com.ade.restapi.UpdateInterface;

/**
 * @author Administrator
 * @version 1.0
 * @created 10-����-2011 ���� 08:33:52
 */
public class SiteManager {

	private static SiteManager instance=null;
	private List<Site> sites;
	private Context context;

	private SiteManager(){
		sites=new ArrayList<Site>(2);
	}

	/**
	 * 
	 * @param site
	 */
	private void addSite(Site site){
		sites.add(site);
	}

	public static SiteManager getInstance(){
		if (instance==null){
			instance=new SiteManager();
		}
		return instance;
	}

	public List<Site> getSites(){
		return sites;
	}

	public boolean loadSites(){
		makeSite();
		return true;
	}

	private Site makeSite(){
		Site site=new SohuSite();
		site.setUpdateInterface(new SohuUpdate());
		site.setUploadInterface(new SohuUpload());
		site.setFriendsTimeline(new SohuFriendsTimeline(new FriendsTimelineParser()));
		addSite(site);
		return site;
	}

	/**
	 * 
	 * @param site
	 */
	private void removeSite(Site site){
		sites.remove(site);
	}

	public void saveSites(){
		
	}
	
	public void setContext(Context context){
		this.context=context;
	}

}