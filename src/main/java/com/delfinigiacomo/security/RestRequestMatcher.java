package com.delfinigiacomo.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class RestRequestMatcher implements RequestMatcher
{
	public boolean matches(HttpServletRequest request)
	{
		// Enables for all the requests ...
		return false;
	}
}
