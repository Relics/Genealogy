package com.example.wbfamilytree;

import com.google.gson.Gson;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity{
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
				Log.e("dd", "etFatherID.getText() :"+etFatherID.getText());
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
				
				String jp=appContext.getJP(AddActivity.this, name);
				if(jp!=null&&!jp.equals(""))
				{
					
					appContext.insertJPS(AddActivity.this, name, jp+";"+person);
				}
				else
				{
					appContext.insertJPS(AddActivity.this, name,person);
				}
				setResult(0);
				finish();
			}
		});
	}

}
