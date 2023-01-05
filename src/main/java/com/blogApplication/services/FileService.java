package com.blogApplication.services;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	//MultipartFile from this we will get original file name
		//form this we will get old path(String path)means the path we are passing
		String uploadImage(String path,MultipartFile file) throws Exception;
		
		
		//to download image
		InputStream getResource(String path,String fileName)throws FileNotFoundException;
		
		
}
