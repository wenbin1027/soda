package com.ade.site;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;

import com.ade.parser.SinaFriendsTimelineParser;
import com.ade.parser.SinaUpdateParser;
import com.ade.parser.SohuFriendsTimelineParser;
import com.ade.parser.SohuUpdateParser;
import com.ade.parser.SohuUploadParser;
import com.ade.restapi.SinaFriendsTimeline;
import com.ade.restapi.SinaUpdate;
import com.ade.restapi.SinaUpload;
import com.ade.restapi.SohuFriendsTimeline;
import com.ade.restapi.SohuUpdate;
import com.ade.restapi.SohuUpload;

public class SiteManager {
	public static final int SOHU=0;
	public static final int SINA=1;
	private static SiteManager instance=null;
	private List<Site> sites;
	private List<Site> sinasites;
	private List<Site> sohusites;
	private Context context;

	private SiteManager(Context context){
		this.context=context;
		sites=new ArrayList<Site>(2);
	}
	
	private SiteManager(){
		sites=new ArrayList<Site>(2);
	}

	public static SiteManager getInstance(){
		if (instance==null){
			instance=new SiteManager();
		}
		return instance;
	}
	
	public static SiteManager getInstance(Context context){
		if (instance==null){
			instance=new SiteManager(context);
		}
		return instance;
	}
	
	public List<Site> getSites(){
		return sites;
	}
	
	public Site getSite(int siteType){
		if (sites.isEmpty()){
			return null;
		}
		if (siteType==SOHU){
			return sites.get(SOHU);
		}
		else if (siteType==SINA){
			return sites.get(SINA);
		}
		else{
			return null;
		}
	}

	public boolean loadSites(){
		sites.add(SOHU, makeSite(SOHU));
		sites.add(SINA, makeSite(SINA));
		return true;
	}

	private Site makeSite(int type){
		Site site=null;

		switch(type){
		case SOHU:
			site=new SohuSite();
			site.setUpdateInterface(new SohuUpdate(new SohuUpdateParser()));
			site.setUploadInterface(new SohuUpload(new SohuUploadParser()));
			site.setFriendsTimeline(new SohuFriendsTimeline(new SohuFriendsTimelineParser()));
			break;
		case SINA:
			site=new SinaSite();
			site.setUpdateInterface(new SinaUpdate(new SinaUpdateParser()));
			site.setUploadInterface(new SinaUpload(new SinaUpdateParser()));
			site.setFriendsTimeline(new SinaFriendsTimeline(new SinaFriendsTimelineParser()));
			break;
		}
		
		if (context!=null){
			ConnectivityManager cm = (ConnectivityManager)context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netinfo = cm.getActiveNetworkInfo(); 
			if(site!=null && netinfo!=null && netinfo.getType()!=ConnectivityManager.TYPE_WIFI){
				site.setProxy(Proxy.getDefaultHost(), Proxy.getDefaultPort());
			}
		}
		return site;
	}

	public void saveSites(){
		
	}
	
	public void setContext(Context context){
		this.context=context;
	}

}