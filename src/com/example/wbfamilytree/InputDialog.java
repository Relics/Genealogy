package com.example.wbfamilytree;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;



public class InputDialog extends Dialog {
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
	
	private EditText input01;
	private EditText input02;
	

	
	public InputDialog(Context context,String hit01,String hit02,String bt1,String bt2,OnButtonLeftClick mOnButtonLeftClick,OnButtonRightClick mOnButtonRightClick) {
		super(context,R.style.MyDialog);
		this.context=context;
		this.title=hit01;
		this.content=hit02;
		
		this.bt1=bt1;
		this.bt2=bt2;
		
		this.mOnButtonLeftClick=mOnButtonLeftClick;
		this.mOnButtonRightClick=mOnButtonRightClick;
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_dialog);
		signBtn=(Button) findViewById(R.id.diaglog_sign_btn);
		signBtnShare=(Button) findViewById(R.id.diaglog_sign_btn_share);
		 input01=(EditText) findViewById(R.id.input01);		
		 input02=(EditText) findViewById(R.id.input02);
		    
		if(title!=null)
		{
			
			
			input01.setHint(title);
		}
		else
		{
			input01.setHint("");
		}
		if(content!=null)
		{
			
			input02.setHint(content);
		}else
		{
			input02.setHint("");
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
		
		
	   
		signBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				//InputDialog.this.dismiss();
				//获取验证码
				
				/*if(!isGet){
				    new Thread(new ThreadShow()).start();  
				    isGet=true;
					if(mOnButtonLeftClick != null)
					{
						mOnButtonLeftClick.onclick(input01.getText().toString(),"btn1");
					}
				}*/
				//InputDialog.this.dismiss();
				
				if(mOnButtonLeftClick != null)
				{
					mOnButtonLeftClick.onclick(input01.getText().toString(),input02.getText().toString());
				}
			}
			
		});
		
			
			signBtnShare.setOnClickListener(new View.OnClickListener(){
				
				@Override
				public void onClick(View v) {
					if(mOnButtonRightClick!=null)
					{
						
						mOnButtonRightClick.onclick(input01.getText().toString(),input02.getText().toString());
					}
					
				}
				
			});
		
	}
	boolean isGet = false;
	 Handler handlerRead = new Handler() {  
	        public void handleMessage(Message msg) {  
	            if (msg.what == 1) {  
	            	int mi = (Integer)msg.obj;
	            	if(mi<=0){
	            		isGet=false;
		            	signBtn.setText("没有收到,继续获取");
	            	}else{
		            	signBtn.setText(mi+"s");
	            	}
	            }  
	        }; 
	    };  
	  
	    // 线程类  
	class ThreadShow implements Runnable {  
	  
	        @Override  
	        public void run() {  
	            // TODO Auto-generated method stub  
	        	int count=60;
	            while (true) {  
	                try {  
	                    Thread.sleep(1000*1);  
	                    Message msg = new Message();  
	                    msg.what = 1; 
	                    count--;
	                    msg.obj =count; 
	                    handlerRead.sendMessage(msg);  
	                    if(count<=0){
	                    	System.out.println("send..."); 
	                    	break;
	                    }
	                } catch (Exception e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
	  public interface OnButtonLeftClick
	  {
		  public void onclick(String input01,String input02);
	  
	  }
	  public interface OnButtonRightClick
	  {
		  public void onclick(String input01,String input02);
		  
	  }
}
