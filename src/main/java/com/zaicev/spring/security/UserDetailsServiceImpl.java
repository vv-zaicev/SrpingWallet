package com.zaicev.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zaicev.spring.security.dao.UserDAO;
import com.zaicev.spring.security.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.getUserByName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + "is not found"));
		return new UserDetailsImpl(user.getUsername(), user.getPassword(), user.getRoles(), user.isEnabled());
	}

}
