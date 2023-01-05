package com.blogApplication.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApplication.payloads.ApiResponse;
import com.blogApplication.payloads.UserDto;
import com.blogApplication.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//POST-Create_User
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto = userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//PUT-Update_User
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Long userId){
		
		UserDto updateUserDto = this.userService.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updateUserDto);
	}

	//AMDIN
	//DELETE-Delete_User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	
	
	//GET-get_One_User
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId){
		
		UserDto user = this.userService.getUserById(userId);
		
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
		
		//Or
		//return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	//GET-Get_All_Users
	
		@GetMapping("/")
		public ResponseEntity<List<UserDto>> getAllUsers(){
			List<UserDto> users = this.userService.getAllUsers();
			return new ResponseEntity<List<UserDto>>(users,HttpStatus.OK);
			
			//Or
		//	return ResponseEntity.ok(this.userService.getAllUsers());
			
		}
}
