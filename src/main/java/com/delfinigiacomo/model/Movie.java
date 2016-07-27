package com.delfinigiacomo.model;


public class Movie {
	

	protected int id;	
    protected String author;	
	protected String title;
	
	// Default constructor ...
//	public Movie() 
//	{
//		super();
//	}
//	
//	public Movie(int id) 
//	{
//		this.id = id;
//	}

//	public Movie(String author, String title) 
//	{
//		this.author = author;
//		this.title = title;
//	}
//	
//	public Movie(int id, String author, String title) 
//	{
//		this.id = id;
//		this.author = author;
//		this.title = title;
//	}

	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getAuthor() 
	{
		return author;
	}
	
	public void setAuthor(String author) 
	{
		this.author = author;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
}
