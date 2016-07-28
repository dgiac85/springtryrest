package com.delfinigiacomo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.delfinigiacomo.model.Movie;

@Component
public class MovieDAO {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public List<Movie> getMoviesUserByActor(String email, String actor)
	{
				
		StringBuilder query = new StringBuilder("SELECT idMov,author,title FROM movies m, use_mov um ")
			                                .append("WHERE m.id=um.idMov AND um.emause=? AND m.id IN (")
			                                .append("SELECT idMov FROM actors a, act_mov am ")
			                                .append("WHERE a.id = am.idAct AND a.name=?)");		 
		
		List<Movie> movies = jdbcTemplate.query(query.toString(),new Object[]{email,actor}, new RowMapper<Movie>(){
			public Movie mapRow(ResultSet rs, int num) throws SQLException
			{
					
				Movie m=new Movie();
				m.setId(rs.getInt("idMov"));
				m.setAuthor(rs.getString("author"));
				m.setTitle(rs.getString("title"));
				
				return m;
			}
		});
		
		return movies;	
	}

}
