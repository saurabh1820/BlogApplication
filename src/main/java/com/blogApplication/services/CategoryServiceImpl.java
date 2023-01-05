package com.blogApplication.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.entities.Category;
import com.blogApplication.exceptions.ResourceNotFoundException;
import com.blogApplication.payloads.CategoryDto;
import com.blogApplication.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//POST_CreateOrSave
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category category2 = this.categoryRepository.save(category);
		return this.modelMapper.map(category2, CategoryDto.class);
	}

	//PUT_Update
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category category2 = this.categoryRepository.save(category);
		
		return this.modelMapper.map(category2, CategoryDto.class);
	}

	//DELETE
	@Override
	public void deleteCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId", categoryId));
		this.categoryRepository.delete(category);
	}

	//GET_ONE
	@Override
	public CategoryDto getCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	//Get_ALL
	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepository.findAll();
		List<CategoryDto> categoryDto = categories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDto;
	}

	

}
