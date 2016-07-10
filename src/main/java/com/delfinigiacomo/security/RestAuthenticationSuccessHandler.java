package com.delfinigiacomo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * This class is used to return 200 instead of 301 code when authentication is successful
 * Must be registered as authentication-success-handler-ref in spring security form-login 
 * configuration
 */

public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler 
{
    private RequestCache requestCache = new HttpSessionRequestCache();
    
    private final static Logger LOG = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class.getName());
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
      Authentication authentication) throws ServletException, IOException {
    	
    	UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   	
    	LOG.info("User {} authenticated successfully", user.getUsername());
    	
    	// Sets the session timeout to infinite ...
    	request.getSession().setMaxInactiveInterval(-1);
    	    	    	
        SavedRequest savedRequest = requestCache.getRequest(request, response);
 
        if (savedRequest == null) 
        {
            clearAuthenticationAttributes(request);
            return;
        }
        
        String targetUrlParam = getTargetUrlParameter();
        
        if (isAlwaysUseDefaultTargetUrl() || 
        		(targetUrlParam != null && 
          StringUtils.hasText(request.getParameter(targetUrlParam)))) 
        {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }
 
        clearAuthenticationAttributes(request);
    }
 
    public void setRequestCache(RequestCache requestCache) 
    {
        this.requestCache = requestCache;
    }
}
