package com.ade.site;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;

import com.ade.parser.SinaAccountVerifyParser;
import com.ade.parser.SinaFriendsTimelineParser;
import com.ade.parser.SinaUpdateParser;
import com.ade.parser.SohuAccountVerifyParser;
import com.ade.parser.SohuFriendsTimelineParser;
import com.ade.parser.SohuUpdateParser;
import com.ade.parser.SohuUploadParser;
import com.ade.restapi.SinaAccountVerify;
import com.ade.restapi.SinaFriendsTimeline;
import com.ade.restapi.SinaUpdate;
import com.ade.restapi.SinaUpload;
import com.ade.restapi.SohuAccountVerify;
import com.ade.restapi.SohuFriendsTimeline;
import com.ade.restapi.SohuUpdate;
import com.ade.restapi.SohuUpload;

public class SiteManager {
	public static final int SOHU=0;
	public static final int SINA=1;
	private static SiteManager instance=null;
	private List<Site> sites;
	
	private SiteManager(){
		sites=new ArrayList<Site>(2);
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

	public boolean loadSites(Context context){
		sites.clear();
		sites.add(SOHU, makeSite(SOHU));
		sites.add(SINA, makeSite(SINA));
		
		loadUser(context,sites.get(0));
		loadUser(context,sites.get(1));
		
		loadBlogs(context,sites.get(0));
		loadBlogs(context,sites.get(1));

		return true;
	}

	private Site makeSite(int type){
		Site site=null;
		User user=null;
		switch(type){
		case SOHU:
			site=new SohuSite();
			site.setUpdateInterface(new SohuUpdate(new SohuUpdateParser()));
			site.setUploadInterface(new SohuUpload(new SohuUploadParser()));
			site.setFriendsTimeline(new SohuFriendsTimeline(new SohuFriendsTimelineParser()));
			site.setAccountInterface(new SohuAccountVerify(new SohuAccountVerifyParser()));

//			user=new User();
//			user.setScreenName("zhang");
//			user.setID(123);
//			user.setAccessToken("46b571ca341cfeb4737f419ed4ce0392");
//			user.setAccessSecret("920143c011048ab9e4c8904440e7ed1a");
//			site.logIn(user);
			break;
		case SINA:
			site=new SinaSite();
			site.setUpdateInterface(new SinaUpdate(new SinaUpdateParser()));
			site.setUploadInterface(new SinaUpload(new SinaUpdateParser()));
			site.setFriendsTimeline(new SinaFriendsTimeline(new SinaFriendsTimelineParser()));
			site.setAccountInterface(new SinaAccountVerify(new SinaAccountVerifyParser()));
//			user=new User();
//			user.setScreenName("wang");
//			user.setID(456);
//			user.setAccessToken("4207a6817f50785a07f456da1f4d20b7");
//			user.setAccessSecret("751c76001bcef5b3c225dbd942c33eaa");
//			site.logIn(user);
			break;
		}

		return site;
	}

	/**
	 * @param site
	 */
	private void loadBlogs(Context context,Site site) {
		if (context!=null && site.isLoggedIn()){
			try {
				FileInputStream in=context.openFileInput(site.getName()+"_"+site.getLoggedInUser().getID());
				byte[] buffer=new byte[in.available()];
				in.read(buffer);
				in.close();
				String text=new String(buffer);
				site.getFriendsTimeline().getParser().parse(text, site);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (context!=null){
			ConnectivityManager cm = (ConnectivityManager)context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netinfo = cm.getActiveNetworkInfo(); 
			if(site!=null && netinfo!=null){
				if (netinfo.getType()!=ConnectivityManager.TYPE_WIFI
						&& netinfo.getExtraInfo().toLowerCase().contains("wap")){
					site.setProxy(Proxy.getDefaultHost(), Proxy.getDefaultPort());
				}
			}
		}
	}
	
	private void loadUser(Context context,Site site) {
		if (context!=null && !site.isLoggedIn()){
			try {
				FileInputStream in=context.openFileInput(site.getName()+".cfg");
				ObjectInputStream stream=new ObjectInputStream(in);
				User user=(User)stream.readObject();
				stream.close();
				in.close();
				if (user!=null){
					site.logIn(user);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveSites(Context context){
		for(Site site:sites){
			if (context!=null && site.isLoggedIn()){
				FileOutputStream out;
				try {
					out = context.openFileOutput(
									site.getName()+"_"+site.getLoggedInUser().getID(), 
									Context.MODE_PRIVATE);
					site.saveBlogs(out);
					out.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					out = context.openFileOutput(
									site.getName()+".cfg", 
									Context.MODE_PRIVATE);
					site.saveUser(out);
					out.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else if (context!=null && !site.isLoggedIn()){
				File file=new File(context.getFilesDir().getAbsolutePath()+"/"+site.getName()+".cfg");
				file.delete();
			}
		}
	}
}