package com.example.project;

import com.example.project.Security.Jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
class JWTGenerationValidationTests {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    @Mock
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void testTokenGeneration() {
        // Generate a token
        String username="TestUser";
        String token = jwtUtils.generateTokenFromUsername(username);
        // Assert that the token is not null and has the correct format
        assertNotNull(token);
        boolean tok = Pattern.matches(".*\\..*\\..*",token);
        assertTrue(tok);
    }

    @Test
    public void testTokenUsername(){
        // Generate a token
        String username="TestUser";
        String token = jwtUtils.generateTokenFromUsername(username);
        // Assert that the token is not null and has the correct format
        assertNotNull(token);
        String name=jwtUtils.getUserNameFromJwtToken(token);
        assertEquals(name,username);
    }

    @Test
    public void testTokenValidationTrue(){
        String token = jwtUtils.generateTokenFromUsername("TestUser");
        assertNotNull(token);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    public void testTokenValidationFalse(){
        String token="abc.de.ef";
        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void contextLoads() {
    }

}
