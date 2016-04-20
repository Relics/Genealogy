package com.example.wbfamilytree;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class ButtonDialog extends AlertDialog {
	Context context;
	Button signBtn;
	Button signBtnShare;
	String title,content;
	Handler handler;
	WebView webView=null;
	private String bt1;
	private String bt2;
	private OnButtonLeftClick mOnButtonLeftClick;
	private OnButtonRightClick mOnButtonRightClick;
	public static final  int BUTTON_LEFT=1;
	public static final  int BUTTON_RIGHT=2;
	private TextView Tv_title;
	private TextView Tv_content;
	private String parameter;
	private int position=-1;

	/**
	 * 传来handler what是MainDialog.BUTTON_LEFT，MainDialog.BUTTON_RIGHT；
	 * @param context
	 * @param title
	 * @param message
	 * @param bt1
	 * @param bt2
	 * @param handler
	 */
	public ButtonDialog(Context context,String title,String content,String bt1,String bt2,Handler handler) {
		super(context,R.style.MyDialog);
		this.context=context;
		this.title=title;
		this.content=content;
		this.handler=handler;
		this.bt1=bt1;
		this.bt2=bt2;
	}
	public ButtonDialog(Context context,String title,String content,String bt1,String bt2,Handler handler,String parameter) {
		super(context,R.style.MyDialog);
		this.context=context;
		this.title=title;
		this.content=content; 
		this.handler=handler;
		this.bt1=bt1;
		this.bt2=bt2;
		this.parameter = parameter;
	}
	public ButtonDialog(Context context,String title,String content,String bt1,String bt2,Handler handler,String parameter,int position) {
		super(context,R.style.MyDialog);
		this.context=context;
		this.title=title;
		this.content=content; 
		this.handler=handler;
		this.bt1=bt1;
		this.bt2=bt2;
		this.parameter = parameter;
		this.position=position;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_dialog);
		signBtn=(Button) findViewById(R.id.diaglog_sign_btn);
		signBtnShare=(Button) findViewById(R.id.diaglog_sign_btn_share);
		Tv_title=(TextView) findViewById(R.id.dialog_sign_title);
		Tv_content=(TextView) findViewById(R.id.dialog_sign_message);
		if(title!=null)
		{
			
			Tv_title.setText(title);
		}
		else
		{
			Tv_title.setVisibility(View.INVISIBLE);
		}
		if(content!=null)
		{
			Tv_content.setText(content);
		}else
		{
			Tv_content.setVisibility(View.INVISIBLE);
		}
		if(bt2==null)
		{
			signBtnShare.setVisibility(View.INVISIBLE);
			
		}
		else
		{
			signBtnShare.setText(bt2);
		}
		if(bt1==null)
		{
			signBtn.setVisibility(View.INVISIBLE);
			
		}
		else
		{
			signBtn.setText(bt1);
		}
		
		
	
		final AppContext  appContext =(AppContext) context.getApplicationContext();
		 
		signBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				ButtonDialog.this.dismiss();
				if(handler!=null){
					Message message = new Message();   
					message.what =ButtonDialog.BUTTON_LEFT;   
					if(parameter!=null){
						message.obj = parameter;
					}
					if(position!=-1){
						message.arg1 =position;
					}
					
					handler.sendMessage(message);   
				} 
			}
			
		});
		
			
			signBtnShare.setOnClickListener(new View.OnClickListener(){
				
				@Override
				public void onClick(View v) {
					ButtonDialog.this.dismiss();
					if(handler!=null){
						Message message = new Message();   
						message.what =ButtonDialog.BUTTON_RIGHT;   
						if(parameter!=null){
							message.obj = parameter;
						}
						if(position!=-1){
							message.arg1 =position;
						}
						handler.sendMessage(message);   
					} 
					
				}
				
			});
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
  public interface OnButtonLeftClick
  {
	  public void onclick();
  
  }
  public interface OnButtonRightClick
  {
	  public void onclick();
	  
  }
}
