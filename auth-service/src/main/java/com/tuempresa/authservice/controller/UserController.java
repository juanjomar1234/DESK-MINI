package com.tuempresa.authservice.controller;

import com.tuempresa.authservice.dto.UserDto;
import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
            .map(this::mapToDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, mapToUser(userDto));
        return ResponseEntity.ok(mapToDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USERS')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setRoles(user.getRoles().stream()
            .map(role -> role.getName())
            .collect(Collectors.toSet()));
        return dto;
    }

    private User mapToUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.isEnabled());
        return user;
    }
} 