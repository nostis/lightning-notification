package com.nostis.security;

import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@TestPropertySource(properties = {
        "jwt.secret=secret",
})
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("h2db")
public class JwtTokenUtilTest {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private UserDetails userDetails;

    @Before
    public void setUp() {
        userDetails = new User("username", "password", Collections.emptyList());
    }

    @Test
    public void whenGenerateToken_thenGenerateValidToken() {
        String token = jwtTokenUtil.generateToken(userDetails);

        Assert.assertTrue(token.contains("."));
    }

    @Test
    public void whenGetUsernameFromToken_thenReturnProperUsername() {
        String token = jwtTokenUtil.generateToken(userDetails);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        Assert.assertEquals("username", username);
    }

    @Test
    public void whenGetExpirationDateFromToken_thenReturnProperExpirationDate() {
        String token = jwtTokenUtil.generateToken(userDetails);
        Long expiration = jwtTokenUtil.getClaimFromToken(token, Claims::getExpiration).getTime() / 100;
        Long expected = (System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY * 1000) / 100;

        //10 is difference between calls
        Assert.assertTrue(expiration < expected + 10 && expiration > expected - 10);
    }

    @Test
    public void whenValidateToken_thenReturnTrue() {
        String token = jwtTokenUtil.generateToken(userDetails);
        Assert.assertTrue(jwtTokenUtil.validateToken(token, userDetails));
    }

    @Test
    public void whenValidateToken_thenReturnFalse() {
        String token = jwtTokenUtil.generateToken(new User("different", "different", Collections.emptyList()));
        Assert.assertFalse(jwtTokenUtil.validateToken(token, userDetails));
    }
}
