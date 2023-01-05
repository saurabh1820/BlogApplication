package com.blogApplication.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogApplication.entities.User;
import com.blogApplication.exceptions.ResourceNotFoundException;
import com.blogApplication.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		//loading user from database by username
	 User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "email"+username, 0));
		
		
		return user;
	}

}
