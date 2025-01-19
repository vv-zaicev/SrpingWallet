package com.zaicev.spring.security;

import java.util.Optional;

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
		Optional<User> user = userDAO.getUserByName(username);
		return user.map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + "is not found"));
	}

}
