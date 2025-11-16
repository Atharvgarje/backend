package com.learning.backend.service;

import org.modelmapper.ModelMapper;

import com.learning.backend.dto.PostRequestDTO;
import com.learning.backend.dto.PostResponseDTO;
import com.learning.backend.exception.UserNotFoundException;
import com.learning.backend.model.Post;
import com.learning.backend.model.User;
import com.learning.backend.repository.PostRepository;
import com.learning.backend.repository.UserRepository;

public class PostService {

	private final PostRepository postRepo;
	private final UserRepository userRepo;
	
	private final ModelMapper modelMapper;

	public PostService(PostRepository postRepo, UserRepository userRepo, ModelMapper modelMapper) {
		super();
		this.postRepo = postRepo;
		this.userRepo = userRepo;
		this.modelMapper = modelMapper;
	}
	
	public PostResponseDTO createPost(PostRequestDTO req)
	{
		User user = userRepo.findById(req.getUserId()).orElseThrow(()->new UserNotFoundException(0l));
		
		Post post = new Post();
		post.setTitle(req.getTitle());
		post.setUser(user);
		
		Post saved = postRepo.save(post);
		
		PostResponseDTO dto = modelMapper.map(saved, PostResponseDTO.class);
		dto.setUserId(user.getId());
		
		return dto;
	}
	
	
}
