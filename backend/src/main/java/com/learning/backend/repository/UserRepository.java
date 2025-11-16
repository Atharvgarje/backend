package com.learning.backend.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.backend.dto.UserRequestDTO;
import com.learning.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User save(UserRequestDTO u);
	
	Optional<User> findByEmail(String email);
	List<User> findByNameContaining(String keyword);
	
	Page<User> findAll(Pageable pageable);

}
