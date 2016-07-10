package com.delfinigiacomo.model;

public class Greeting {
    private final long id;
    private final String content;
    private final String app;

    public Greeting(long id, String content, String app) 
    {
        this.id = id;
        this.content = content;
        this.app = app;
    }

    public long getId() 
    {
        return id;
    }

    public String getContent() 
    {
        return content;
    }
    
    public String getApp() 
    {
    	return app;
    }
}
