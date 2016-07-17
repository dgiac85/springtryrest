package com.delfinigiacomo.dao;

/*UserDAO è la classe che fa da Data Access Object per gli utenti*/

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.delfinigiacomo.model.Account;

@Component
public class UserDAO
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public List<Account> getUsers()
	{
		String query = "select * from users, user_role where user_role.email = users.email";
		
		List<Account> users = jdbcTemplate.query(query, new RowMapper<Account>(){
			public Account mapRow(ResultSet rs, int num) throws SQLException
			{
				Account u = new Account();
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setRole(rs.getString("role"));
				u.setActive(rs.getInt("active"));
				return u;
			}
		});
		
		return users;	
	}
	
	public Account findByUsername(String username) 
	{
		// Gets user's details and role from the database ...
		String query = "select * from users, user_role where users.email = ? and user_role.email = users.email";
		//new Object[]{username} è il parametro da inviare e va messo nel queryForObject
		Account u = jdbcTemplate.queryForObject(query, new Object[]{ username }, new RowMapper<Account>(){
			public Account mapRow(ResultSet rs, int num) throws SQLException
			{
				Account u = new Account();
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setRole(rs.getString("role"));
				u.setActive(rs.getInt("active"));
				return u;
			}
		});
		
		return u;
	}
}
