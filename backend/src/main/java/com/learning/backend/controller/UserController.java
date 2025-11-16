package com.learning.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.backend.config.ModelMapperConfig;
import com.learning.backend.dto.UserPatchDTO;
import com.learning.backend.dto.UserRequestDTO;
import com.learning.backend.dto.UserResponseDTO;
import com.learning.backend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final ModelMapperConfig modelMapperConfig;
    private final UserService service;

    public UserController(UserService service, ModelMapperConfig modelMapperConfig) {
        this.service = service;
        this.modelMapperConfig = modelMapperConfig;
    }

    // ------------------ CREATE ------------------
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO u) {
        UserResponseDTO dto = service.createUser(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // ------------------ GET ALL ------------------
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> dto = service.getAllUsers();
        return ResponseEntity.ok(dto);
    }

    // ------------------ PAGINATION (STATIC) ------------------
    @GetMapping("/paged")
    public ResponseEntity<Page<UserResponseDTO>> getPaged(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(service.getPagedUsers(page, size));
    }

    // ------------------ SEARCH (STATIC) ------------------
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.searchByName(keyword));
    }

    // ------------------ GET BY EMAIL (STATIC) ------------------
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getUserByEmail(email));
    }

    // ------------------ GET BY ID (DYNAMIC) ------------------
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        UserResponseDTO dto = service.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    // ------------------ UPDATE ------------------
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UserRequestDTO u) {
        UserResponseDTO dto = service.updateUser(id, u);
        return ResponseEntity.ok(dto);
    }

    // ------------------ PARTIAL UPDATE ------------------
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> partialUpdate(
            @PathVariable Long id,
            @RequestBody UserPatchDTO req) {
        return ResponseEntity.ok(service.partialUpdate(id, req));
    }

    // ------------------ DELETE ------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }
    
    @GetMapping("/sorted")
    public ResponseEntity<Page<UserResponseDTO>> getSorted(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String direction) {

        return ResponseEntity.ok(service.getSortedUsers(page, size, sortBy, direction));
    }

}
