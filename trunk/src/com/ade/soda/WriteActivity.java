package com.ade.soda;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.ade.site.Site;
import com.ade.site.SiteListener;
import com.ade.site.SiteManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class WriteActivity extends Activity implements OnClickListener,
		SiteListener {
	private Site site;
	private final int BEGIN = 0;
	private final int ERROR = 1;
	private final int END = 2;
	private Dialog progressDlg;
	private Bitmap myBitmap;
	private Boolean haveImage = false;
	private byte[] mContent;
	private ImageView imageView;
	private final int CAMERA = 3;
	private final int ALBUM = 4;
	private final int LIST_DIALOG = 5;
	private String filename;
	private int toBeSendCount=1;

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
		imageView = (ImageView) findViewById(R.id.shrinkPic);
		imageView.setOnClickListener(this);
	}

	private Handler mainHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case BEGIN:
				if (progressDlg == null) {
					progressDlg = ProgressDialog.show(WriteActivity.this,
							getResources().getString(R.string.waitDialogTitle),
							getResources().getString(R.string.waitSendMessage),
							true, true, new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									site.abort();
								}
							});
				}
				break;
			case END:
				toBeSendCount--;
				if (toBeSendCount<=0){
					dismissDlg();
					Toast.makeText(WriteActivity.this,
							getResources().getString(R.string.SendSuccess),
							Toast.LENGTH_SHORT).show();
					setResult(RESULT_OK);
					WriteActivity.this.finish();
				}
				break;
			case ERROR:
				toBeSendCount--;
				if (toBeSendCount<=0){
					dismissDlg();
					if (msg.obj != null) {
						Toast.makeText(WriteActivity.this,
								getResources().getString(R.string.SendError),
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			return true;
		}

		/**
		 * 
		 */
		private void dismissDlg() {
			if (progressDlg != null) {
				progressDlg.dismiss();
				progressDlg = null;
			}
		}
	});

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.BtnImg:
			showDialog(LIST_DIALOG);
			break;
		case R.id.BtnFace:
			// todo invoke face
			break;
		case R.id.Btnwriteback:

			WriteActivity.this.finish();
			break;
		case R.id.BtnSendMsg:
			if (site==null){
				toBeSendCount=SiteManager.getInstance().getSites().size();
				for(Site tempSite:SiteManager.getInstance().getSites()){
					sendMsg(tempSite);
				}
			}
			else{
				toBeSendCount=1;
				sendMsg(site);
			}
			break;
		case R.id.shrinkPic:
			AlertDialog.Builder exitAlertDialog = new AlertDialog.Builder(this);
			exitAlertDialog.setTitle(getResources().getString(R.string.delete)).setMessage(getResources().getString(R.string.isdelete))
			.setPositiveButton(getResources().getString(R.string.dialogOK),new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					imageView.setImageBitmap(null);
					haveImage = false;
					imageView.setVisibility(View.GONE);   
				}
				})
				.setNegativeButton(getResources().getString(R.string.dialogCancel),new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
					}})
					.setCancelable(false).create().show();
			break;
		default:
			break;
		}
	}

	private void sendMsg(Site site) {
		EditText mEditText = (EditText) findViewById(R.id.EditText);
		String s = mEditText.getText().toString().trim();
		if (s.length() <= 0)
			Toast.makeText(WriteActivity.this,
					getResources().getString(R.string.PleaseWrite),
					Toast.LENGTH_SHORT).show();
		else {
			site.addListener(this);
			if (!haveImage){
				site.updateText(s);
			}
			else{
				try {
					site.uploadImage(getFileStreamPath(filename).getAbsolutePath(), s==null?"":s);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onBeginRequest() {
		mainHandler.sendEmptyMessage(BEGIN);
	}

	@Override
	public void onError(String errorMessage) {
		Message msg = new Message();
		msg.what = ERROR;
		msg.obj = errorMessage;
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
		b.setItems(R.array.msa, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent getImageByCamera = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					startActivityForResult(getImageByCamera, CAMERA);
					break;
				case 1:
					Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
					getImage.addCategory(Intent.CATEGORY_OPENABLE);
					getImage.setType("image/jpeg");
					startActivityForResult(getImage, ALBUM);
					break;
				default:
					break;
				}

			}
		});
		dialog = b.create();
		return dialog;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		ContentResolver resolver = getContentResolver();

		if (requestCode == ALBUM && resultCode==RESULT_OK) {
			Uri originalUri = data.getData();
			filename=(new Date()).toLocaleString();
			try {
				FileOutputStream os=this.openFileOutput(filename, MODE_PRIVATE);
				mContent = readStream(resolver.openInputStream(Uri
						.parse(originalUri.toString())));
				os.write(mContent);
				os.close();	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			myBitmap = getPicFromBytes(mContent, null);
			imageView.setImageBitmap(myBitmap);
			haveImage = true;
			imageView.setVisibility(View.VISIBLE);

		} else if (requestCode == CAMERA && resultCode==RESULT_OK) {
			super.onActivityResult(requestCode, resultCode, data);
			Bundle extras = data.getExtras();
			myBitmap = (Bitmap) extras.get("data");
			filename=(new Date()).toLocaleString();
			try {
				FileOutputStream fOut;
				fOut = this.openFileOutput(filename,MODE_PRIVATE);
		        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		        fOut.flush();
		        fOut.close();
	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageView.setImageBitmap(myBitmap);
			haveImage = true;
			imageView.setVisibility(View.VISIBLE);
		} 
	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}
	
	@Override
	protected void onDestroy() {
		for(Site tempSite:SiteManager.getInstance().getSites()){
			tempSite.removeListener(this);
		}
		super.onDestroy();
	}

	/**
	 * 
	 */
	private void deleteFile() {
		if (filename!=null){
			File file=getFileStreamPath(filename);
			file.delete();
			filename=null;
		}
	}
}
