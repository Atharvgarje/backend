package com.learning.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
	
	@NotBlank(message="Name is required")
	private String name;
	
	@Email(message="Invalid format")
	@NotBlank(message="Email is required")
	private String email;

}
