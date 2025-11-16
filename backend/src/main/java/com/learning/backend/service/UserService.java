package com.learning.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.learning.backend.dto.PostSummaryDTO;
import com.learning.backend.dto.UserPatchDTO;
import com.learning.backend.dto.UserRequestDTO;
import com.learning.backend.dto.UserResponseDTO;
import com.learning.backend.exception.UserNotFoundException;
import com.learning.backend.model.User;
import com.learning.backend.repository.UserRepository;

@Service
public class UserService {

    private final ModelMapper modelMapper;
	
	private final UserRepository repo;

	public UserService(UserRepository repo,ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.repo = repo;
	}
	
	public UserResponseDTO createUser(UserRequestDTO req)
	{
		 User u = modelMapper.map(req, User.class);
		    User saved = repo.save(u);

		    return modelMapper.map(saved, UserResponseDTO.class);
	}
	
	
	

	public List<UserResponseDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return repo.findAll()
			    .stream()
			    .map(u -> modelMapper.map(u, UserResponseDTO.class))
			    .toList();

	}
	
	public UserResponseDTO getUserByEmail(String email)
	{
		User u = repo.findByEmail(email).orElseThrow(() -> new UserNotFoundException(0l));
		return modelMapper.map(u, UserResponseDTO.class);
	}
	
	public UserResponseDTO getUserById(Long id)
	{
		User u= repo.findById(id).orElseThrow(()-> new UserNotFoundException(id));
		
		UserResponseDTO dto= modelMapper.map(u, UserResponseDTO.class);
		
		List<PostSummaryDTO> postdto=u.getPosts().stream().map(post -> modelMapper.map(post, PostSummaryDTO.class)).toList();
		
		dto.setPosts(postdto);
		
		return dto;

	}
	
	public UserResponseDTO partialUpdate(Long id, UserPatchDTO req)
	{
		User existing = repo.findById(id).orElseThrow(()->new UserNotFoundException(id));
		if(req.getName()!=null) {
			existing.setName(req.getName());
		}
		if(req.getEmail() != null) {
			existing.setEmail(req.getEmail());
		}
		User saved=repo.save(existing);
		
		return modelMapper.map(saved, UserResponseDTO.class);
	}
	
	public List<UserResponseDTO> searchByName(String keyword){
		List<User> users=repo.findByNameContaining(keyword);
		return users.stream().map(u->modelMapper.map(u, UserResponseDTO.class)).toList();
	}
	
	public UserResponseDTO updateUser(Long id, UserRequestDTO req) {

	    User existing = repo.findById(id)
	            .orElseThrow(() -> new UserNotFoundException(id));

	    // Step 1: update entity using DTO
	   modelMapper.map(req, existing);

	    // Step 2: save updated entity
	    User saved = repo.save(existing);

	    // Step 3: convert saved entity to response DTO
	   
	    return modelMapper.map(saved, UserResponseDTO.class);
	}

	
	public void deleteUser(Long id)
	{
		if(!repo.existsById(id))
		{
			throw new RuntimeException("User not found");
		}
		repo.deleteById(id);
	}
	
	public Page<UserResponseDTO> getPagedUsers(int page,int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		
		return repo.findAll(pageable).map(u-> modelMapper.map(u, UserResponseDTO.class));
	}
	
	public Page<UserResponseDTO> getSortedUsers(int page,int size,String sortBy,String direction)
	{
		Sort sort=direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size,sort);
		
		return repo.findAll(pageable).map(u->modelMapper.map(u, UserResponseDTO.class));
	}
	

}
