package com.delfinigiacomo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{	
	@Autowired
	RestUserDetailService restUserDetailService;
	
	@Autowired
	protected CORSFilter corsFilter;
	

	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
			// Disable CSRF protection ...
			// .csrf().disable() //DA SPRING 3 il CSRF E' GIA INSERITO AUTOMATICAMENTE
			.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.authenticationEntryPoint(new RestAuthenticationEntryPoint())
			.and()
			.formLogin()
			.successHandler(new RestAuthenticationSuccessHandler())
			.failureHandler(new SimpleUrlAuthenticationFailureHandler())
			.and()
			.logout()
			.logoutSuccessHandler(new RestLogoutSuccessHandler());
		
		// CORS filter ...
		http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
		
		// CSRF tokens handling ...
        http.addFilterAfter(new CsrfTokenFilter(), CsrfFilter.class);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(restUserDetailService);
	}
}
