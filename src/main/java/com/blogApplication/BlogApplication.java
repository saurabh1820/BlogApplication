package com.blogApplication;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogApplication.config.AppConstants;
import com.blogApplication.entities.Role;
import com.blogApplication.repositories.RoleRepository;

import antlr.collections.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("xyz"));
		
		try {
			
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			ArrayList<Role> roles=new ArrayList<>();
			roles.add(role);
			roles.add(role1);
			
			java.util.List<Role> result = this.roleRepository.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
}
