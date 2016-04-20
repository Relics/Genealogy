package com.example.wbfamilytree;

import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppContext extends Application{
	public static final String SP_NAME="sp";
	public static final String SP_NAME_PWD="sppwd";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public void insertJPS(Context context,String key,String value)
	{
		SharedPreferences sp=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	public Map<String,String> getJPS(Context context)
	{
		SharedPreferences sp=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
		return (Map<String, String>) sp.getAll();
	}
	public String getJP(Context context,String key)
	{
		SharedPreferences sp=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
		return sp.getString(key, "");
		
	}
	public void removeJP(Context context,String key)
	{
		SharedPreferences sp=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}
	public void setPWD(Context context,String key,String value)
	{
		SharedPreferences sp=context.getSharedPreferences(SP_NAME_PWD,Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	public String getPWD(Context context,String key)
	{
		SharedPreferences sp=context.getSharedPreferences(SP_NAME_PWD,Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
		
	
}
