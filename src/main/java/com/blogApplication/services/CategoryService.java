package com.blogApplication.services;

import java.util.List;

import com.blogApplication.payloads.CategoryDto;

public interface CategoryService {

	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	public CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	
	//delete
	public void deleteCategory(Long categoryId);
	
	//get
	public CategoryDto getCategory(Long categoryId);
	
	//get_All
	public List<CategoryDto> getCategories();
}
