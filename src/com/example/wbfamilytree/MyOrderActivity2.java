package com.example.wbfamilytree;

import java.util.ArrayList;
import java.util.List;

import com.example.wbfamilytree.InputDialog.OnButtonLeftClick;
import com.example.wbfamilytree.InputDialog.OnButtonRightClick;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrderActivity2 extends Activity implements OnItemClickListener{
	private ListView lv;
	private TextView title;
	private View add;
	private View show;
	private View p;

	private AppContext appContext;
	private List<Person> data;
	private String name;
	private View jm;
	private InputDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumehistory);
		p=findViewById(R.id.main_title_showNum);
		title=(TextView) p.findViewById(R.id.main_title_showNum_text);
		name=getIntent().getStringExtra("name");
		title.setText(""+name);
	
		lv=(ListView) findViewById(R.id.listview);
		lv.setOnItemClickListener(this);
		
		add=findViewById(R.id.add);
		
		appContext=(AppContext) getApplicationContext();
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPerson();
			}
		});
		show=findViewById(R.id.show);
		show.setVisibility(View.VISIBLE);
		show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent intent=new Intent(MyOrderActivity2.this,ShowActivity.class);
					intent.putExtra("name", name);
					startActivity(intent);
			}
		});
		
		jm=findViewById(R.id.jm);
		jm.setVisibility(View.VISIBLE);
		jm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 dialog=new InputDialog(MyOrderActivity2.this, "请输入密码", "", "确定", "取消", new OnButtonLeftClick() {
						
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
		String jp=appContext.getJP(this,name);
		
		Log.e("dd", jp.toString()+"   f");
		
		if(jp.equals(""))
		{
			return;
		}
		String[] ps=jp.split(";");
		Log.e("dd", ps.length+"   length");
		data=new ArrayList<>();
		Gson gson=new Gson();
		for (String string : ps) {
		  Person p=	gson.fromJson(string, Person.class);
		  data.add(p);
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
	                convertView = View.inflate(MyOrderActivity2.this,
	                        R.layout.listitem10, null);
	                new ViewHolder(convertView);
	            }
	            ViewHolder holder = (ViewHolder) convertView.getTag();
	       
	            if(data.size()>0)
	            {
	            	
	            	holder.tv_title.setText(data.get(position).getName());
	            	holder.tv_content.setText(data.get(position).getBirthday());
	            }
	           
	           
	           
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
		                tv_content=(TextView) view.findViewById(R.id.content);
		                view.setTag(this);
	            }
	        }

		private void addPerson()
		{
			
			Intent intent=new Intent(MyOrderActivity2.this,AddActivity.class);
			intent.putExtra("name",name);
			startActivityForResult(intent, 0);
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(MyOrderActivity2.this,AddActivity2.class);
			Person p=data.get(arg2);
			Gson gson=new Gson();
			String json=gson.toJson(p);
			intent.putExtra("name",name);
			intent.putExtra("json",json+"");
			startActivityForResult(intent,0);
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			initData();
		}
		
		private void addJp(String input)
		{
			if(input==null||input.equals(""))
			{
				Toast.makeText(this,"密码不能为空",0).show();
				return;
			}
			appContext.setPWD(MyOrderActivity2.this, name, input);
			Toast.makeText(this,"设置成功",0).show();
			
		}
	    
}
