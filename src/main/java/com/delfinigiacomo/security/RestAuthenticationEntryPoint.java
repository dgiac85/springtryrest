package com.delfinigiacomo.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * This class registered spring framework and configured as entry-point-ref in http 
 * spring security configuration allows to return a proper code (401) when a client 
 * visit a page and it's not authorized. Spring Security by default redirects to the 
 * login page with a 301 code
 */

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	@Override
	public void commence( HttpServletRequest request, HttpServletResponse response, 
	    AuthenticationException authException ) throws IOException
	 {
	      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	 }
}
