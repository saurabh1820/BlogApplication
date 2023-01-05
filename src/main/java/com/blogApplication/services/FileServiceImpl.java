package com.blogApplication.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws Exception {
		//File name
				String name=file.getOriginalFilename();
				
				//random name generate file
				String randomId=UUID.randomUUID().toString();
				String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));
				
				//Full path
				String filePath=path+File.separator+fileName1;
				
				//create folder if not created
				File f=new File(path);
				if(!f.exists())
				{
					f.mkdir();
				}
				
				//file copy
				Files.copy(file.getInputStream(), Paths.get(filePath));
				return name;
				
				
				//file.getInputStream() we will get from this and also need to throw the Exception
				//Paths.get(filePath) it is use for where we want to copy the data
		
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullpath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullpath);
		
		return is;
	}

}
