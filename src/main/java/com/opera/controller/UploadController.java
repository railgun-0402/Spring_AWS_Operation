package com.opera.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Controller
@RequestMapping("/upload")
public class UploadController {
	@Autowired
    private AmazonS3 amazonS3;
	
	@PostMapping
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID().toString();
		String bucketName = "spring-bucket-su"; // 適当なバケット名
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        
        return "redirect:/upload"; // ホームページにリダイレクト	
	}
	
	@GetMapping
    public String showUploadForm() {
        return "upload"; // アップロードフォームを表示するHTMLページへのリダイレクト
    }

}
