package com.learning.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserResponseDTO {
	
	private Long id;
	private String name;
	private String email;
	 private List<PostSummaryDTO> posts;

}
