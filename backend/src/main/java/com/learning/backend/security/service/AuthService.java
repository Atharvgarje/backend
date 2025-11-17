package com.learning.backend.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.backend.security.model.AuthUser;
import com.learning.backend.security.model.RegisterRequest;
import com.learning.backend.security.model.Role;
import com.learning.backend.security.repository.AuthRepository;

@Service
public class AuthService {

    private final AuthRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(AuthRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }
    
    public String register(RegisterRequest req)
    {
    	if(repo.findByEmail(req.getEmail()).isPresent())
    	{
    		throw new RuntimeException("Email already exists");
    	}
    	
    	AuthUser user = new AuthUser();
    	user.setEmail(req.getEmail());
    	user.setPassword(encoder.encode(req.getPassword()));
    	user.setRole(Role.USER);
    	
    	repo.save(user);
    	
    	return "REgistered Successfully";
    }
}
