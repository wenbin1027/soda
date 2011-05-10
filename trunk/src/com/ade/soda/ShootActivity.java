package com.ade.soda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class ShootActivity extends Activity implements SurfaceHolder.Callback{
	public static int CAMERA_WIDTH = 320;
	public static int CAMERA_HEIGHT = 436;
	boolean isShown = false;
	Camera camera = null;			
	SurfaceView sv;					
	byte [] photoData = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 	
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.shoot);			
		sv = (SurfaceView)findViewById(R.id.svCamera);
		sv.getHolder().addCallback(this);
		sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);		
		ImageButton ib = (ImageButton)findViewById(R.id.ibShoot);
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {					
				camera.takePicture(myShutterCallback, myRawCallback, myjpegCallback);
			}
		});
		ImageButton btnUploadPhoto = (ImageButton)findViewById(R.id.btnUploadPhoto);		
		btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {									
				if(photoData == null){
					return;
				}
				Intent intentSend = ShootActivity.this.getIntent();
				intentSend.setClass(ShootActivity.this,WriteActivity.class);		
				intentSend.putExtra("data", photoData);			
				ShootActivity.this.finish();
				startActivity(intentSend);
			}
		});
		ImageButton btnReShoot = (ImageButton)findViewById(R.id.btnShootBack);			
		btnReShoot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShootActivity.this.finish();
			}
		});
	}
	public void initCamera(){
		if(!isShown){		
			camera = Camera.open();
		}
		if(camera != null && !isShown){
			try{
				Camera.Parameters params = camera.getParameters();
				params.setPictureFormat(PixelFormat.JPEG);		
				params.setPreviewSize(CAMERA_WIDTH,CAMERA_HEIGHT);	
				camera.setParameters(params);
				camera.setPreviewDisplay(sv.getHolder());
				camera.startPreview();
			}catch(Exception e){
				e.printStackTrace();
			}
			isShown = true;
		}
	}
	public void shutdownCamera(){
		isShown = false;
		camera.stopPreview();
		camera.release();
		camera = null;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initCamera();				
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		shutdownCamera();		
	}
	ShutterCallback myShutterCallback = new ShutterCallback(){
		@Override
		public void onShutter(){}
	};
	PictureCallback myRawCallback = new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {}
	};	
	PictureCallback myjpegCallback = new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			photoData = data;			
			ImageView iv = (ImageView)findViewById(R.id.ivCamera);		
			Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			iv.setImageBitmap(bmp);
			shutdownCamera();			
			initCamera();
		}
	};
}