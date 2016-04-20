package com.example.wbfamilytree;

public class Person {
  private String name;
  private int generations;
  private int row;
  private String sex;
  private String wife;
  private String father;
  private String mather;
  private String husband;
  private String birthday;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getGenerations() {
	return generations;
}
public void setGenerations(int generations) {
	this.generations = generations;
}
public int getRow() {
	return row;
}
public Person(String name, int generations, int row) {
	super();
	this.name = name;
	this.generations = generations;
	this.row = row;
}
public void setRow(int row) {
	this.row = row;
}
public String getFather() {
	return father;
}
public void setFather(String father) {
	this.father = father;
}
public String getMather() {
	return mather;
}
public void setMather(String mather) {
	this.mather = mather;
}
public String getWife() {
	return wife;
}
public void setWife(String wife) {
	this.wife = wife;
}
public Person(String name, int generations, int row, String father, String mather, String wife) {
	super();
	this.name = name;
	this.generations = generations;
	this.row = row;
	this.father = father;
	this.mather = mather;
	this.wife = wife;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getHusband() {
	return husband;
}
public void setHusband(String husband) {
	this.husband = husband;
}
public Person(String name, int generations, int row, String sex, String wife, String father, String mather,
		String husband) {
	super();
	this.name = name;
	this.generations = generations;
	this.row = row;
	this.sex = sex;
	this.wife = wife;
	this.father = father;
	this.mather = mather;
	this.husband = husband;
}
public Person() {
	super();
}
public String getBirthday() {
	return birthday;
}
public void setBirthday(String birthday) {
	this.birthday = birthday;
}
  
  
}
