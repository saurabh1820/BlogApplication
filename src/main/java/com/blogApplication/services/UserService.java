package com.blogApplication.services;

import java.util.List;

import com.blogApplication.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	
	//Create_User(POST)
	UserDto createUser(UserDto userDto);
	
	//Update_User(PUT)
	UserDto updateUser(UserDto userDto,Long userId);
	
	//Get_User(GET)
	UserDto getUserById(Long userId);
	
	//Get_All_User(GET)
	List<UserDto> getAllUsers();
	
	//Delete_User(DELETE)
	void deleteUser(Long userId);
}
