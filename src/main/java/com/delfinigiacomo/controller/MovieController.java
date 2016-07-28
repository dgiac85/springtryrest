package com.delfinigiacomo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delfinigiacomo.dao.MovieDAO;
import com.delfinigiacomo.model.Movie;

@Api(value="Movies operations")
@RestController
public class MovieController {
	
	@Autowired
	protected MovieDAO movieDAO;
	
	private final static Logger LOG = LoggerFactory.getLogger(MainController.class.getName());
	
	//////////////////////////////////////////////////////////////////////////////
	/*My Apis*////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	@ApiOperation(notes="Method used for getting the user's favourite movies list of a specific actor", value = "/moviesuserbyactor", code=200)
	@RequestMapping(value = "/movieuserbyactor", method = RequestMethod.GET, produces="application/json")
	public List<Movie>  moviesUserByActor(
		@ApiParam(name = "email", value = "email", required = true) @RequestParam(name = "email", required=true) String email,
		@ApiParam(name = "actor", value = "actor", required = true) @RequestParam(name = "actor", required=true) String actor
		) throws Exception 
	{
			LOG.info("MainController getMoviesUserByActor method invoked ...");
			return movieDAO.getMoviesUserByActor(email,actor);
	}

}
