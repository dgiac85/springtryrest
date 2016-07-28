package com.delfinigiacomo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.delfinigiacomo.model.UserInfo;

import com.delfinigiacomo.dao.UserDAO;
import com.delfinigiacomo.model.Account;

//E' la classe che mi permette di conoscere i dettagli dell'utente registrato nel db

@Component
public class RestUserDetailService implements UserDetailsService
{
	@Autowired
	protected UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		// Load user's information from the database ...
		Account account;
		
		try
		{
			account = userDAO.findByUsername(username);  //TROVO L'UTENTE ATTRAVERSO L'USERNAME
		}
		catch(Exception x)
		{
			throw new UsernameNotFoundException("Could not find the user '" + username + "'");
		}
		
		if(account != null) 
		{
			String role;
			String email = account.getEmail();
			String password = account.getPassword();
			boolean isActive = account.getActive() == 1 ? true : false;
			  
			if(account.getRole().equalsIgnoreCase("user"))
				role = "ROLE_USER";
			else if(account.getRole().equalsIgnoreCase("admin"))
				role = "ROLE_ADMIN";
			else if(account.getRole().equalsIgnoreCase("superuser"))
				role = "ROLE_SUPERUSER";
			else if(account.getRole().equalsIgnoreCase("superuser-ro"))
				role = "ROLE_SUPERUSER_RO";
			else
				role = "ROLE_USER";
			  
			return new User(email, 
					password, 
					isActive,
					true, // Is account not Expired ...
					true, // Is credential not expired ...
					true,	// Is not locked ...
					getAuthorities(role)); //IN QUESTO MODO DO' IL RUOLO ALL'UTENTE E CHIAMO IL METODO UTILE ADD AGGIUNGERE
					//AD AUTHLIST IL RUOLO SPECIFICO DELL'UTENTE
	
		} 
		else 
		{
			//SE CI STA UN ERRORE LANCIAMO UNA USERNAMENOTFOUNDEXCEPTION
			throw new UsernameNotFoundException("Could not find the user '" + username + "'");
		}
	}
	
	protected List<GrantedAuthority> getAuthorities(String role) 
	{
	    List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
	    authList.add(new SimpleGrantedAuthority(role));
	    return authList;
	}
}
