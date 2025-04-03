package com.tuempresa.authservice.controller;

import com.tuempresa.authservice.dto.AuthRequest;
import com.tuempresa.authservice.dto.AuthResponse;
import com.tuempresa.authservice.dto.PasswordChangeRequest;
import com.tuempresa.authservice.dto.UserDto;
import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.security.JwtTokenProvider;
import com.tuempresa.authservice.service.AuditService;
import com.tuempresa.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final AuditService auditService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userService.getUserByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.isPasswordChangeRequired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "message", "Password change required",
                    "requirePasswordChange", true
                ));
        }

        String token = tokenProvider.generateToken(authentication);
        auditService.logEvent(user.getUsername(), "LOGIN", "Usuario inició sesión exitosamente");

        return ResponseEntity.ok(AuthResponse.builder()
            .token(token)
            .username(request.getUsername())
            .roles(authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new))
            .build());
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody PasswordChangeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        userService.changePassword(
            userDetails.getUsername(),
            request.getCurrentPassword(),
            request.getNewPassword()
        );

        auditService.logEvent(
            userDetails.getUsername(),
            "PASSWORD_CHANGE",
            "Usuario cambió su contraseña"
        );

        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        User user = userService.createUser(mapToUser(userDto));
        return ResponseEntity.ok(mapToDto(user));
    }

    private User mapToUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.isEnabled());
        return user;
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        return dto;
    }
} 