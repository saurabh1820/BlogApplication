package com.blogApplication.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogApplication.config.AppConstants;
import com.blogApplication.entities.Role;
import com.blogApplication.entities.User;
import com.blogApplication.exceptions.*;
import com.blogApplication.payloads.UserDto;
import com.blogApplication.repositories.RoleRepository;
import com.blogApplication.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//Creating User
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		
		User saveUser = userRepository.save(user);
		
		return this.userToDto(saveUser);
	}
	
	//Updating User
	@Override
	public UserDto updateUser(UserDto userDto, Long userId){
		
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User user2 = userRepository.save(user);
			
		return this.userToDto(user2);
	}

	//Getting One_User
	@Override
	public UserDto getUserById(Long userId) {
		
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
			
		return this.userToDto(user);
	}

	//Getting All_User
	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = userRepository.findAll();
		
		List<UserDto> userDtos = users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	//Delete One_User
	@Override
	public void deleteUser(Long userId) {
		
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		userRepository.delete(user);
	}
	
	//Convert DTO To Entity
	public User dtoToUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
//		User user=new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	//Convert Entity To DTO
	public UserDto userToDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
	
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User user2 = this.userRepository.save(user);
		
		return this.modelMapper.map(user2, UserDto.class);
	}

}
