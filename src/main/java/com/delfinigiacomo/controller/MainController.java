package com.delfinigiacomo.controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.delfinigiacomo.config.Config;
import com.delfinigiacomo.dao.UserDAO;
import com.delfinigiacomo.model.Greeting;
import com.delfinigiacomo.model.RequestException;
import com.delfinigiacomo.model.Account;

@Api(value="Rest Boot operations")
@RestController
public class MainController {
	
	@Autowired
	protected Config config;
	
	@Autowired
	protected UserDAO userDAO;
	
	private final static Logger LOG = LoggerFactory.getLogger(MainController.class.getName());
	
	@ApiOperation(notes="Method used for testing purposes", value = "/hello", code=200)
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(
			) throws Exception 
	{
		LOG.info("MainController hello() method invoked ...");
		return "Hello from SPRING BOOT!!!";
	}
	
	@ApiOperation(notes="Method used for testing purposes", value = "/greeting", code=200)
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(
    		@ApiParam(name = "name", value = "first parameter", required = true) @RequestParam(value="name", defaultValue="World") String name
    		) throws Exception 
    {
    	LOG.info("MainController greeting() method invoked ...");
        return new Greeting(1, "Hello World!", config.getDescription());
    }
	
	@ApiOperation(notes="Method used for testing purposes", value = "/test", code=200)
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Hashtable<String, String> test(
			@ApiParam(name = "param1", value = "param1 description", required = true) @RequestParam("param1") String param1,
			@ApiParam(name = "param2", value = "param2 description", required = true) @RequestParam("param2") String param2,
			@ApiParam(name = "param3", value = "param3 description", required = true) @RequestParam("param3") String param3) throws Exception
	{
		LOG.info("ExecController method TEST invoked.");
		Hashtable<String, String> ht = new Hashtable<String, String>();
		
		ht.put("PARAM3", param3);
		ht.put("PARAM1", param1);
		ht.put("PARAM2", param2);
		
		return ht;
	}
	
	@ApiOperation(notes="Method used for listing users", value = "/users", code=200)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Account> getUsers() throws Exception
    {
    	LOG.info("MainController getUsers() method invoked ...");
    	return userDAO.getUsers(); //getUsers ritorna praticamente una lista di accounts
    }
    
	@ApiOperation(notes="Method used for getting user's details", value = "/users", code=200)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Account getUser(
    		@ApiParam(name = "email", value = "user's email", required = true) @RequestParam(value="email") String email
    		) throws Exception
    {
    	LOG.info("MainController getUser() method invoked ...");
    	return userDAO.findByUsername(email);
    }
	
	///////////////////////////////////////////////////////////////////////////
	// This method convert any RequestException into a 400 HTTP response, with
	// the body set to the message set when the exception has been thrown ...
	///////////////////////////////////////////////////////////////////////////
	
	@ExceptionHandler(RequestException.class)
	public String handleUncaughtRequestException(Exception ex, WebRequest request, HttpServletResponse response) throws IOException 
	{
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return "{\"error\":\"" + ex.getMessage() + "\"}";
	}
		
	///////////////////////////////////////////////////////////////////////////
	// This method convert any DataAccessException into a 501 HTTP response, with
	// the body set to the message set when the exception has been thrown ...
	///////////////////////////////////////////////////////////////////////////
	
	@ExceptionHandler(DataAccessException.class)
	public String handleUncaughtDataAccessException(Exception ex, WebRequest request, HttpServletResponse response) throws IOException 
	{
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return "{\"error\":\"" + ex.getMessage() + "\"}";
	}
}
