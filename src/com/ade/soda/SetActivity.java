package com.ade.soda;

import java.util.List;

import com.ade.site.Site;
import com.ade.site.SiteManager;
import com.ade.site.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;	
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SetActivity extends Activity {
	private final int AUTHREQUESTCODE = 0;
	private ListView mListView;
	private List<Site> sites;
	private	String[] siteNames;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		
		// Initialize ListView
		sites=SiteManager.getInstance().getSites();
		siteNames=new String[sites.size()];
		for(int i=0;i<sites.size();i++){
			siteNames[i]=sites.get(i).getName();
		}
		
		mListView = (ListView) findViewById(R.id.ListView01);
		mListView.setAdapter(
			new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_multiple_choice, siteNames
			)
		);
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mListView.setOnItemClickListener(this.mListCheckListener);
		
		loadListViewState();
	}
	
	// helper method	
	// Load ListView state
	private void loadListViewState() {
		for(int i = 0; i < mListView.getCount(); i++){
			mListView.setItemChecked(i, siteAuthenticated(sites.get(i)));
		}
	}
	
	// helper method
	// Check if a site has an access key.
	private Boolean siteAuthenticated(Site site){
		Boolean result = false;

		if(!site.isLoggedIn()){
			result = false;
		}else{
			result = true;
		}
		
		return result;
	}

	private OnItemClickListener mListCheckListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
			// Get item check state
			SparseBooleanArray selectedIndexes = mListView.getCheckedItemPositions();
			Boolean itemChecked = selectedIndexes.get(arg2);

			if(itemChecked) {
				if(!siteAuthenticated(sites.get(arg2))) {
					// Pass the site ID to OAuthActivity
					Intent intent = new Intent(SetActivity.this, OAuthActivity.class);
					intent.putExtra("siteID", sites.get(arg2).getSiteID());
					intent.putExtra("user", new User());
					startActivityForResult(intent, AUTHREQUESTCODE);
				
					loadListViewState();
				}
			} else {
				// Confirm to invalidate the access key
				AlertDialog.Builder bld = new AlertDialog.Builder(SetActivity.this);
				
				// TODO Move the strings to resource
				bld.setTitle(String.format("确定禁用\"%s\"帐户吗?再次启用需要重新验证。", sites.get(arg2).getName()));
				bld.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	invalidateAuthentication(sites.get(arg2));
		            }
		        });
		        bld.setNegativeButton("取消", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	Toast.makeText(SetActivity.this, "取消", Toast.LENGTH_SHORT).show();
		            }
		        });
		        
				bld.show();
			}
		}
	};
	
	// helper method
	// Invalidate the authentication for selected site
	private void invalidateAuthentication(Site site) {
		site.logOut();
		//TODO:
		Toast.makeText(SetActivity.this, "TODO: Invalidate access key " + site.getName(), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AUTHREQUESTCODE) {
			if (resultCode == RESULT_OK) {
				if (data.hasExtra("user")) {
					User user = (User) data.getSerializableExtra("user");
					int siteID=data.getIntExtra("siteID",SiteManager.SINA);
					SiteManager.getInstance().getSite(siteID).logIn(user);
				}
			}
			Toast.makeText(this,
					resultCode == RESULT_OK ? "Auth Success" : "Auth Fail",
					Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}