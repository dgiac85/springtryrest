package com.delfinigiacomo.model;


public class Actor {


	protected int id;
	protected String name;

	
	protected Actor() 
	{
		super();
	}
	
	public Actor(int id)
	{
		super();
		this.id = id;
	}


	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}


}
