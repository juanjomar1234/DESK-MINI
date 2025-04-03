package com.tuempresa.authservice.security;

import com.tuempresa.authservice.config.BaseTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest extends BaseTestConfig {

    private static final String TEST_SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long TEST_EXPIRATION = 3600000L; // 1 hora

    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "jwtSecret", TEST_SECRET);
        ReflectionTestUtils.setField(tokenProvider, "jwtExpirationInMs", TEST_EXPIRATION);
    }

    @Test
    void generateTokenAndValidate() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "testuser", null, Collections.emptyList());
        
        String token = tokenProvider.generateToken(auth);
        assertThat(token).isNotNull();
        assertThat(tokenProvider.validateToken(token)).isTrue();
        assertThat(tokenProvider.getUsernameFromToken(token)).isEqualTo("testuser");
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        String invalidToken = "invalid.token.here";
        assertThat(tokenProvider.validateToken(invalidToken)).isFalse();
    }

    @Test
    void validateToken_ExpiredToken_ReturnsFalse() {
        ReflectionTestUtils.setField(tokenProvider, "jwtExpirationInMs", -86400000L);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            "testUser",
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String token = tokenProvider.generateToken(authentication);
        assertThat(tokenProvider.validateToken(token)).isFalse();
    }
} 