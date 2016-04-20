package com.example.wbfamilytree;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShowActivity extends Activity{
	private RelativeLayout vg;
	private TextView title;
	private View share;
	private IWXAPI api;
	public static final String APP_ID = "wx1e3aa65621a191e0";
	private static final int THUMB_SIZE = 150;

	private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
	private static final int MMAlertSelect1  =  0;
	private static final int MMAlertSelect2  =  1;
	private static final int MMAlertSelect3  =  2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this,APP_ID);
		api.registerApp(APP_ID);
		
		
		setContentView(R.layout.activity_show);
		vg=(RelativeLayout) findViewById(R.id.group);
		String name=getIntent().getStringExtra("name");
		MyView v=new MyView(this,name);
		vg.addView(v);
		title=(TextView)findViewById(R.id.main_title_showNum_text);
		title.setText("家谱图");
		
		share=findViewById(R.id.add);
		
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bitmap bmp = convertViewToBitmap(vg);
				WXImageObject imgObj = new WXImageObject(bmp);
				
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = imgObj;
				
				Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
				bmp.recycle();
				msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // ��������ͼ

				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("img");
				req.message = msg;
				req.scene =  SendMessageToWX.Req.WXSceneTimeline ;//: SendMessageToWX.Req.WXSceneSession;
				api.sendReq(req);
				
			}
		});
		
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
	public static Bitmap convertViewToBitmap(View view){
		//view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
     //   view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view. setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		
		
	        	return bitmap;
	}
}
