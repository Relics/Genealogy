package com.example.wbfamilytree;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class AddActivity2 extends Activity{
	private TextView title;
	private EditText etname;
	private EditText etBirthday;
	private EditText etZrLevel;
	private EditText etRank;
	private EditText etFatherID;
	private EditText tvMotherID;
	private EditText spouse;
	private RadioGroup rg;
	private String sex="男";
	private View save;
	private AppContext appContext;
	private String name;
	private Person mPerson;
	private String json;
	private RadioButton rbnan;
	private RadioButton rbnv;
	private List<Person> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_zredit);
		title=(TextView)findViewById(R.id.main_title_showNum_text);
		name=getIntent().getStringExtra("name");
		title.setText("添加家谱成员");
		etname=(EditText) findViewById(R.id.etCname);
		etBirthday=(EditText) findViewById(R.id.etBirthday);
		etZrLevel=(EditText) findViewById(R.id.etZrLevel);
		etRank=(EditText) findViewById(R.id.etRank);
		etFatherID=(EditText) findViewById(R.id.etFatherID);
		tvMotherID=(EditText) findViewById(R.id.etMotherID);
		spouse=(EditText) findViewById(R.id.spouse);
		rbnan=(RadioButton) findViewById(R.id.rbSex1);
		rbnv=(RadioButton) findViewById(R.id.rbSex2);
		
		initData();
		appContext=(AppContext) getApplicationContext();
		
		rg=(RadioGroup) findViewById(R.id.rgp);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rbSex1:
					sex="男";
					break;
				case R.id.rbSex2:
					sex="女";
					break;

				default:
					break;
				}
			}
		});
		
		save=findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Person  p=new Person();
				p.setName(etname.getText().toString().trim());
				p.setSex(sex);
				p.setBirthday(etBirthday.getText().toString().trim());
				p.setGenerations(Integer.parseInt(etZrLevel.getText().toString().trim()));
				p.setRow(Integer.parseInt(etRank.getText().toString().trim()));
				p.setFather(etFatherID.getText().toString().trim());
				p.setMather(tvMotherID.getText().toString().trim());
				if(sex.equals("女"))
				{
					p.setHusband(spouse.getText().toString().trim());
				}
				else
				{
					
					p.setWife(spouse.getText().toString().trim());
				}
				Gson gson=new Gson();
				String person=gson.toJson(p);
				
				String jp=appContext.getJP(AddActivity2.this, name);
				
				Log.e("dd","add2 :" +jp+  " NAME:"+name);
				String[] ps=jp.split(";");
				
				data=new ArrayList<Person>();
				
				for (String string : ps) {
				  Person p2=	gson.fromJson(string, Person.class);
				  data.add(p2);
				}
				for (int i = 0; i < data.size(); i++) {
					if(data.get(i).getName().equals(mPerson.getName()))
					{
						data.remove(i);
						break;
					}
				}
				
				StringBuffer sb=new StringBuffer();
				for (int j = 0; j < data.size(); j++) {
					String gs=gson.toJson(data.get(j));
					if(j==data.size()-1)
					{
						sb.append(gs);
						
					}
					else
					{
						sb.append(gs+";");
					}
					
				}
				jp=sb.toString();
				Log.e("dd","add2 :" +jp);
				if(jp!=null&&!jp.equals(""))
				{
					
					appContext.insertJPS(AddActivity2.this, name, jp+";"+person);
				}
				else
				{
					appContext.insertJPS(AddActivity2.this, name,person);
				}
				setResult(0);
				finish();
			}
		});
	}
private void initData() {
    Gson gson=new Gson();
    json=getIntent().getStringExtra("json");
    
	mPerson=gson.fromJson(json, Person.class);
	
	etname.setText(mPerson.getName());
	etBirthday.setText(mPerson.getBirthday());
	etZrLevel.setText(mPerson.getGenerations()+"");
	etRank.setText(mPerson.getRow()+"");
	etFatherID.setText(mPerson.getFather());
	tvMotherID.setText(mPerson.getMather());
	if(mPerson.getSex().equals("男"))
	{
		
		spouse.setText(mPerson.getWife());
		rbnan.setChecked(true);
		rbnv.setChecked(false);
	}
	else
	{
		spouse.setText(mPerson.getHusband());
		rbnan.setChecked(false);
		rbnv.setChecked(true);
	}
	
	}

}
