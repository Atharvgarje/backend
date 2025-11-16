package com.learning.backend.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class ApiError {
	
	private int status;
	private String error;
	private String message;
	private Instant timestamp;
	private List<String> details;
	public ApiError(int status, String error, String message, Instant timestamp, List<String> details) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
		this.timestamp = timestamp;
		this.details = details;
	}
	
	

}
