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
	
	
	/*
	 * A quanto pare la echo usa una classe UserDetails di Spring
	 * Dopo ciò usa una classe UserInfo e setta l'utente e il ruolo	 * 
	 * UserInfo è un file che si trova all'interno del package Model
	 * Quindi setta l'utente prendendo l'username e la password dall'istanza user della classe 
	 * UserDetails
	 * */
}
