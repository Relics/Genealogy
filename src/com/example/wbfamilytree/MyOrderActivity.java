package com.example.wbfamilytree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.wbfamilytree.InputDialog.OnButtonLeftClick;
import com.example.wbfamilytree.InputDialog.OnButtonRightClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrderActivity extends Activity implements OnItemClickListener,OnItemLongClickListener{
	private ListView lv;
	private TextView title;
	private View add;
	private View p;
	private InputDialog dialog;
	private AppContext appContext;
	private List<JP> data;
	private int id=-1;
	private boolean ispwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumehistory);
		p=findViewById(R.id.main_title_showNum);
		title=(TextView) p.findViewById(R.id.main_title_showNum_text);
		
		title.setText("家谱列表");
	
		lv=(ListView) findViewById(R.id.listview);
		lv.setOnItemClickListener(this);
		lv.setOnItemLongClickListener(this);
		add=findViewById(R.id.add);
		
		appContext=(AppContext) getApplicationContext();
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 dialog=new InputDialog(MyOrderActivity.this, "请输入家谱名称", "", "确定", "取消", new OnButtonLeftClick() {
					
					@Override
					public void onclick(String input01, String input02) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						addJp(input01);
					}
				}, new OnButtonRightClick() {
					
					@Override
					public void onclick(String input01, String input02) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				 dialog.show();
			}
		});
		
		initData();
	}
	
	private void initData() {
		Map<String,String> jps=appContext.getJPS(this);
		data=new ArrayList<>();
		JP jp=null;
		for ( Entry<String,String>  e: jps.entrySet()) {
			jp=new JP();
			jp.setName(e.getKey());
			jp.setValue(e.getValue());
			data.add(jp);
		}
		lv.setAdapter(new AppAdapter());
	}

	class AppAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 if (convertView == null) {
	                convertView = View.inflate(MyOrderActivity.this,
	                        R.layout.listitem09, null);
	                new ViewHolder(convertView);
	            }
	            ViewHolder holder = (ViewHolder) convertView.getTag();
	       
	            
	            holder.tv_title.setText(data.get(position).getName());
	           
	           
	           
			return convertView;
		}
		
	}
			
	        class ViewHolder {
	        	 ImageView iv_icon;
		          
		            TextView tv_detail;
		            TextView tv_content;
		            TextView tv_title;
	          

	            public ViewHolder(View view) {
	            	  iv_icon = (ImageView) view.findViewById(R.id.logo);

		               
		                tv_title=(TextView) view.findViewById(R.id.title);
		                view.setTag(this);
	            }
	        }

		private void addJp(String input)
		{
			if(input==null||input.equals(""))
			{
				Toast.makeText(this,"家谱名不能为空",0).show();
				return;
			}
			
			appContext.insertJPS(this, input, "");
			Toast.makeText(this,"添加成功",0).show();
			initData();
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		
			final String pwd=appContext.getPWD(this, data.get(arg2).getName());
			
			if(pwd!=null&&!pwd.equals(""))
			{
				 dialog=new InputDialog(MyOrderActivity.this, "请输入密码", "", "确定", "取消", new OnButtonLeftClick() {
						
						@Override
						public void onclick(String input01, String input02) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							if(input01.equals(pwd))
							{
								Intent intent=new Intent(MyOrderActivity.this,MyOrderActivity2.class);
								
								intent.putExtra("name",data.get(arg2).getName());
								
								startActivity(intent);
							}
							else
							{
								
								Toast.makeText(MyOrderActivity.this, "密码错误",0).show();
							}
						}
					}, new OnButtonRightClick() {
						
						@Override
						public void onclick(String input01, String input02) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					 dialog.show();
				
					 return;
			}
			
			
			
				
				Intent intent=new Intent(MyOrderActivity.this,MyOrderActivity2.class);
				
				intent.putExtra("name",data.get(arg2).getName());
				
				startActivity(intent);
			
			
		}

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			id=arg2;
			ButtonDialog dialog=new ButtonDialog(MyOrderActivity.this,"温馨提示","确定删除吗？", "确定", "取消", mHandler);
			dialog.show();
			return true;
		}
		
	    Handler mHandler=new Handler()
	    		{
	    			public void handleMessage(android.os.Message msg)
	    			{
	    				if(msg.what==ButtonDialog.BUTTON_LEFT)
	    				{
	    					
	    					if(id!=-1)
	    					{
	    						String key=data.get(id).getName();
	    						appContext.removeJP(MyOrderActivity.this, key);
	    						initData();
	    					}
	    				}
	    				
	    			};
	    		};
}
