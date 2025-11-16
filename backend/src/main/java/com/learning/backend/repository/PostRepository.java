package com.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.backend.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
