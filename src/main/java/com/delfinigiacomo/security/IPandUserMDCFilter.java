package com.delfinigiacomo.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

/**
 * Class implementing a Filter which adds user and remote address keys in slf4j MDC
 * in slf4j MDC. Useful for adding these information in application logging ...
 */

@Component
public class IPandUserMDCFilter implements Filter {
	
	private final static Logger LOG=LoggerFactory.getLogger(IPandUserMDCFilter.class.getName());

	protected static final String MDC_REMOTEADDR_KEY = "remoteAddr";
	protected static final String MDC_USER_KEY = "user";
	protected static final String ANONYMOUS = "anonymous";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// The Authentication object may be null, e.g. before the login request ...
		if(auth != null)
		{
			String role = auth.getAuthorities().iterator().next().getAuthority();
					
			// This check is mandatory when we are using anonymous authentication,
			// because in this case we do not have a UserDetails object available ...
			if(role.equalsIgnoreCase("ROLE_ANONYMOUS"))
			{
				MDC.put(MDC_USER_KEY, ANONYMOUS);
				MDC.put(MDC_REMOTEADDR_KEY, request.getRemoteAddr());
			}
			else
			{
				UserDetails user=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
				MDC.put(MDC_USER_KEY, user.getUsername());
				MDC.put(MDC_REMOTEADDR_KEY, request.getRemoteAddr());
			}
		}
		
		try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_USER_KEY);
            MDC.remove(MDC_REMOTEADDR_KEY);
        }
	}

	@Override
	public void init(FilterConfig fc) throws ServletException 
	{
		LOG.info("Called empty init() function on IPandUserMDCFilter");
	}
	
	@Override
	public void destroy() 
	{
		LOG.info("Called empty destroy() function on IPandUserMDCFilter");
	}
}
