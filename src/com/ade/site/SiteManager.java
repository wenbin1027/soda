package com.ade.site;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ade.parser.FriendsTimelineParser;
import com.ade.restapi.SinaFriendsTimeline;
import com.ade.restapi.SinaUpdate;
import com.ade.restapi.SinaUpload;
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
	public static final int SOHU=0;
	public static final int SINA=1;
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
		makeSite(SOHU);
		makeSite(SINA);
		return true;
	}

	private Site makeSite(int type){
		Site site=null;
		switch(type){
		case SOHU:
			site=new SohuSite();
			site.setUpdateInterface(new SohuUpdate());
			site.setUploadInterface(new SohuUpload());
			site.setFriendsTimeline(new SohuFriendsTimeline(new FriendsTimelineParser()));
			addSite(site);
			break;
		case SINA:
			site=new SinaSite();
			site.setUpdateInterface(new SinaUpdate());
			site.setUploadInterface(new SinaUpload());
			site.setFriendsTimeline(new SinaFriendsTimeline(new FriendsTimelineParser()));
			addSite(site);
			break;
		}
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