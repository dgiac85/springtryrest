package com.delfinigiacomo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.delfinigiacomo.model.UserInfo;

@RestController
public class EchoController
{
	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public UserInfo echo()
	{
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserInfo u = new UserInfo();
		u.setUser(user.getUsername());
		u.setRole(user.getAuthorities().iterator().next().getAuthority());
		return u;
	}	
}
