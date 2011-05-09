package com.ade.soda;

import java.util.Set;
import com.ade.restapi.UpdateInterface;
import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.SiteListener;
import com.ade.site.SiteManager;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.Toast;

public class WriteActivity extends Activity implements OnClickListener, SiteListener {
	private Site site;
	private final int BEGIN = 0;
	private final int ERROR = 1;
	private final int END = 2;
	private Dialog progressDlg;
	final int LIST_DIALOG = 2;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);
		Intent intent = getIntent();
		if (intent != null) {
			if (intent.hasExtra("site")) {
				site = SiteManager.getInstance().getSiteByID(
						intent.getIntExtra("site", -1));
			}
		}
		findViewById(R.id.BtnImg).setOnClickListener(this);
		findViewById(R.id.BtnFace).setOnClickListener(this);
		findViewById(R.id.BtnSendMsg).setOnClickListener(this);
		findViewById(R.id.Btnwriteback).setOnClickListener(this);
	}
	private Handler mainHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case BEGIN:
				if (progressDlg==null){
					progressDlg=ProgressDialog.show(
							WriteActivity.this, 
							getResources().getString(R.string.waitDialogTitle),
							getResources().getString(R.string.waitSendMessage),
							true,true,
							new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									site.abort();
								}
							}
							);
				}
				break;
			case END:
				site.removeListener(WriteActivity.this);
				dismissDlg();
				Toast.makeText(WriteActivity.this, getResources().getString(R.string.SendSuccess), Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				WriteActivity.this.finish();
				break;
			case ERROR:
				site.removeListener(WriteActivity.this);
				dismissDlg();
				if (msg.obj!=null){
					Toast.makeText(WriteActivity.this, getResources().getString(R.string.SendError), Toast.LENGTH_SHORT).show();
				}
				break;
			}
			return true;
		}

		/**
		 * 
		 */
		private void dismissDlg() {
			if (progressDlg!=null){
				progressDlg.dismiss();
				progressDlg=null;
			}
		}
	});
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.BtnImg:
			// todo
			showDialog(LIST_DIALOG);
			break;
		case R.id.BtnFace:
			// todo
			break;
		case R.id.Btnwriteback:
			// todo
			WriteActivity.this.finish();
			break;
		case R.id.BtnSendMsg:
			// todo
			sendMsg(site);
			break;
		default:
			break;
		}
	}

	private void sendMsg(Site site) {
		EditText mEditText = (EditText) findViewById(R.id.EditText);		
		String s = mEditText.getText().toString().trim();	
		if (s.length()<=0)
			Toast.makeText(WriteActivity.this, getResources().getString(R.string.PleaseWrite), Toast.LENGTH_SHORT)
					.show();
		else {
			site.addListener(this);
			site.updateText(s);			
		}
		
	}

	@Override
	public void onBeginRequest() {
		mainHandler.sendEmptyMessage(BEGIN);
	}

	@Override
	public void onError(String errorMessage) {
		Message msg=new Message();
		msg.what=ERROR;
		msg.obj=errorMessage;
		mainHandler.sendMessage(msg);
	}

	@Override
	public void onResponsed() {
		mainHandler.sendEmptyMessage(END);
	}
	protected Dialog onCreateDialog(int id) {		
		Dialog dialog = null;
		Builder b = new AlertDialog.Builder(this);	
		b.setIcon(R.drawable.photo);					
		b.setTitle(R.string.img);		
		b.setItems(										
				R.array.msa, 							
				new DialogInterface.OnClickListener() {	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							// todo	
							break;
						case 1:
							// todo
							break;
						default:
							break;
						}
					
					}
				});		
	dialog=b.create();							
	return dialog;
	}
	
	
	
	
}
    

