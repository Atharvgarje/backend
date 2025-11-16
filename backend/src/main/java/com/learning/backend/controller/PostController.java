package com.learning.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.learning.backend.dto.PostRequestDTO;
import com.learning.backend.dto.PostResponseDTO;
import com.learning.backend.service.PostService;

public class PostController {
	
	private final PostService service;

	public PostController(PostService service) {
		super();
		this.service = service;
	}
	
	public ResponseEntity<PostResponseDTO> create(@RequestBody PostRequestDTO req){
		return ResponseEntity.status(201).body(service.createPost(req));
	}

}
