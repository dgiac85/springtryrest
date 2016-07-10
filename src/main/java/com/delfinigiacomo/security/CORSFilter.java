package com.delfinigiacomo.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class CORSFilter implements Filter {

	private final static Logger LOG=LoggerFactory.getLogger(CORSFilter.class.getName());
	
	@Value("${js.allowedOrigins}")
	protected String allowedOrigins;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws ServletException, IOException 
	{
		if(req instanceof  HttpServletRequest && resp instanceof HttpServletResponse)
		{
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) resp;
			
			response.setHeader("Access-Control-Allow-Origin", allowedOrigins); 
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
			response.setHeader("Access-Control-Expose-Headers", "X-CSRF-TOKEN");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			
			if(!request.getMethod().equals("OPTIONS"))
			{
				chain.doFilter(request, response);
			}
		}
		else
			LOG.error("Error filtering HTTP request");
	}

	@Override
	public void destroy() 
	{
		LOG.info("Called empty destroy() function on CORSFilter");
	}

	@Override
	public void init(FilterConfig fc) throws ServletException 
	{
		LOG.info("Called empty init() function on CORSFilter");
	}
}
