package com.delfinigiacomo.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorsController {

	@RequestMapping(value="/401", method=RequestMethod.GET)
	public @ResponseBody ErrorMessage unauthorized()
	{
		return new ErrorMessage("Unauthorized");
	}
	
	@RequestMapping(value="/403", method=RequestMethod.GET)
	public @ResponseBody ErrorMessage forbidden()
	{
		return new ErrorMessage("Unauthorized");
	}
	
	@RequestMapping(value="/404", method=RequestMethod.GET)
	public @ResponseBody ErrorMessage notFound()
	{
		return new ErrorMessage("Not found");
	}
}
