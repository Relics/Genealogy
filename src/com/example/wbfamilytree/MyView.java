package com.example.wbfamilytree;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyView extends View {
	List<Person> ps=new ArrayList<>();
	public static final int x=200;
	public static final int y=100;
	Context context;
	AppContext appContext;
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paintbg=new Paint();
		this.context=context;
		// TODO Auto-generated constructor stub
		Person p0=new Person();
		p0.setName("a");
		p0.setSex("男");
		p0.setGenerations(1);
		p0.setRow(1);
		p0.setWife("a1");
		ps.add(p0);
		Person p1=new Person();
		p1.setName("a1");
		p1.setSex("女");
		p1.setGenerations(1);
		p1.setRow(2);
		p1.setWife("a1");
		ps.add(p1);
		Person p2=new Person();
		p2.setName("b1");
		p2.setSex("男");
		p2.setGenerations(2);
		p2.setRow(1);
		p2.setFather("a");
		p2.setMather("a1");
		p2.setWife("b2");
		ps.add(p2);
		Person p21=new Person();
		p21.setName("b2");
		p21.setSex("女");
		p21.setGenerations(2);
		p21.setRow(2);
		
		ps.add(p21);
	
		
		Person p3=new Person();
		p3.setName("c1");
		p3.setSex("男");
		p3.setGenerations(3);
		p3.setRow(1);
		p3.setMather("b2");
		
		ps.add(p3);
		
		Person p4=new Person();
		p4.setName("d1");
		p4.setSex("男");
		p4.setGenerations(4);
		p4.setRow(6);
		
		
		ps.add(p4);
	
		
	}

	Paint paint;
	Paint paintLine;
	Paint paintbg;

	public MyView(Context context,String name) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paintbg=new Paint();
		paintLine=new Paint();
		
		paintLine.setColor(Color.RED);
		paintLine.setTextSize(10);
		
		this.context=context;
		
		appContext=(AppContext) context.getApplicationContext();
		
		String jp=appContext.getJP(context,name);
		
		Log.e("dd", jp.toString()+"   f");
		
		if(jp.equals(""))
		{
			return;
		}
		String[] persons=jp.split(";");
		Log.e("dd", persons.length+"   length");
	
		Gson gson=new Gson();
		for (String string : persons) {
		  Person p=	gson.fromJson(string, Person.class);
		  ps.add(p);
		}
		
		
		
		
		
		
		
		
		
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
	
		paint.setTextSize(50.0f);
		paint.setColor(Color.BLACK);
	
		paintbg.setColor(context.getResources().getColor(R.color.bgggg));
		
		
		for (int i = 0; i <ps.size(); i++) 
		{
				if(ps.get(i).getSex().equals("男"))
				{
					paintbg.setColor(context.getResources().getColor(R.color.bgggg));
					
				}
				else
				{
					paintbg.setColor(context.getResources().getColor(R.color.bgggg2));
				}
				canvas.drawRect(20+ps.get(i).getRow()*x-Utils.getFontHeight(paint)/3, ps.get(i).getGenerations()*y-Utils.getFontHeight(paint), 20+ps.get(i).getRow()*x, ps.get(i).getGenerations()*y+Utils.getFontHeight(paint)/4, paintbg);
			
				canvas.drawText(ps.get(i).getName(),20+ps.get(i).getRow()*x, ps.get(i).getGenerations()*y, paint);
				if(ps.get(i).getWife()!=null)
				{
					for (int j = 0; j < ps.size(); j++)
					{
						if(ps.get(j).getGenerations()==ps.get(i).getGenerations()&&ps.get(i).getWife().equals(ps.get(j).getName()))
						{
							canvas.drawLine(20+ps.get(i).getRow()*x+Utils.getFontHeight(paint)/4, ps.get(i).getGenerations()*y, 20+ps.get(j).getRow()*x-Utils.getFontHeight(paint)/4, ps.get(j).getGenerations()*y, paintLine);
						}
					}
				}
				
				if(ps.get(i).getFather()!=null)//如果有父亲
				{
					
						for (int k = 0; k < ps.size(); k++) 
						{
							
							if(ps.get(k).getName().equals(ps.get(i).getFather())&&ps.get(i).getGenerations()==ps.get(k).getGenerations()+1)
						
							{
								canvas.drawLine(20+ps.get(i).getRow()*x+Utils.getFontHeight(paint)/4, ps.get(i).getGenerations()*y, 20+ps.get(k).getRow()*x+Utils.getFontHeight(paint)/4, ps.get(k).getGenerations()*y-Utils.getFontHeight(paint)/4, paintLine);
							}
						}
					
				}
				else if(ps.get(i).getMather()!=null)
				{
					for (int k = 0; k < ps.size(); k++) 
					{
						
						if(ps.get(k).getName().equals(ps.get(i).getMather())&&ps.get(i).getGenerations()==ps.get(k).getGenerations()+1)
					
						{
							canvas.drawLine(20+ps.get(i).getRow()*x+Utils.getFontHeight(paint)/4, ps.get(i).getGenerations()*y, 20+ps.get(k).getRow()*x+Utils.getFontHeight(paint)/4, ps.get(k).getGenerations()*y-Utils.getFontHeight(paint)/4, paintLine);
						}
					}
				}
				
				
					
			}
			
				
			
	
	}
	
	
}
