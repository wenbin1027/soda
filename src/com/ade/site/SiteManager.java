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
	public static final int TENCENT=2;
	public static final int NETEASE=3;
	public static final int SITESCOUNT=4;
	private static SiteManager instance=null;
	private List<Site> sites;
	
	private SiteManager(){
		sites=new ArrayList<Site>(SITESCOUNT);
	}

	public static SiteManager getInstance(){
		if (instance==null){
			instance=new SiteManager();
		}
		return instance;
	}
	
	public final List<Site> getSites(){
		return sites;
	}
	
	public Site getSiteByID(int siteID){
		if (sites.isEmpty()){
			return null;
		}
		
		for(int i=0;i<sites.size();i++){
			if (sites.get(i).getSiteID()==siteID)
				return sites.get(i);
		}
		
		return null;
	}

	public boolean loadSites(Context context){
		sites.clear();
		sites.add(makeSite(SINA));
		sites.add(makeSite(SOHU));
		sites.add(makeSite(TENCENT));
		sites.add(makeSite(NETEASE));
		
		loadUser(context,sites.get(SINA));
		loadUser(context,sites.get(SOHU));
		loadUser(context,sites.get(TENCENT));
		loadUser(context,sites.get(NETEASE));
		
		loadBlogs(context,sites.get(SINA));
		loadBlogs(context,sites.get(SOHU));
		loadBlogs(context,sites.get(TENCENT));
		loadBlogs(context,sites.get(NETEASE));

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
			break;
		case SINA:
			site=new SinaSite();
			site.setUpdateInterface(new SinaUpdate(new SinaUpdateParser()));
			site.setUploadInterface(new SinaUpload(new SinaUpdateParser()));
			site.setFriendsTimeline(new SinaFriendsTimeline(new SinaFriendsTimelineParser()));
			site.setAccountInterface(new SinaAccountVerify(new SinaAccountVerifyParser()));
			break;
		case TENCENT:
			site=new TencentSite();
//			site.setUpdateInterface(new SinaUpdate(new SinaUpdateParser()));
//			site.setUploadInterface(new SinaUpload(new SinaUpdateParser()));
//			site.setFriendsTimeline(new SinaFriendsTimeline(new SinaFriendsTimelineParser()));
//			site.setAccountInterface(new SinaAccountVerify(new SinaAccountVerifyParser()));
			break;
		case NETEASE:
			site=new NeteaseSite();
//			site.setUpdateInterface(new SinaUpdate(new SinaUpdateParser()));
//			site.setUploadInterface(new SinaUpload(new SinaUpdateParser()));
//			site.setFriendsTimeline(new SinaFriendsTimeline(new SinaFriendsTimelineParser()));
//			site.setAccountInterface(new SinaAccountVerify(new SinaAccountVerifyParser()));
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
		if (context==null)
			return;
		for(Site site:sites){
			if (site.isLoggedIn()){
				if (site.getBlogsCount()==0){
					File file=new File(
							context.getFilesDir().getAbsolutePath()+
							"/"+site.getName()+
							"_"+site.getLoggedInUser().getID());
					file.delete();
				}
				else{
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
				}	
				FileOutputStream out;
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
			else{
				File file=new File(context.getFilesDir().getAbsolutePath()+"/"+site.getName()+".cfg");
				file.delete();
			}
		}
	}
}