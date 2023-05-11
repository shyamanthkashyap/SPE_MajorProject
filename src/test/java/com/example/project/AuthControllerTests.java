package com.example.project;

import com.example.project.Payload.Request.TokenRefreshRequest;
import com.example.project.Payload.Request.UserLogin;
import com.example.project.Payload.Request.UserSignUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthControllerTests {

    private MockMvc mockMvc;

    private String refreshToken;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Order(1)
    public void UserSignUp() throws Exception {
        UserSignUp userSignUp = new UserSignUp();
        userSignUp.setUsername("Test");
        userSignUp.setPassword("Test");
        userSignUp.setEmail("Test1@gmail.com");
        userSignUp.setLocationId(1L);
        MvcResult result = mockMvc.perform(post("/api/auth/user/signup").content(new ObjectMapper().writeValueAsString(userSignUp)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        String expected = "{\"message\":\"User registered successfully!\"}";
        Assert.assertEquals(expected,resultContent);
    }

    @Test
    @Order(2)
    public void UserSignIn() throws Exception {
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername("Test");
        userLogin.setPassword("Test");
        MvcResult result = mockMvc.perform(post("/api/auth/user/signin").content(new ObjectMapper().writeValueAsString(userLogin)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        int n = resultContent.length();
        resultContent = resultContent.substring(n-8,n-2);
        String expected = "Bearer";
        Assert.assertEquals(expected,resultContent);
    }

    @Test
    @Order(3)
    public void RefreshToken() throws Exception {
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername("Test");
        userLogin.setPassword("Test");
        MvcResult result = mockMvc.perform(post("/api/auth/user/signin").content(new ObjectMapper().writeValueAsString(userLogin)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String[] list = resultContent.split(",")[4].split(":");
        String[] arr = list[1].split("\"");
        refreshToken = arr[1];
        TokenRefreshRequest request = new TokenRefreshRequest();
        request.setRefreshToken(refreshToken);
        result = mockMvc.perform(post("/api/auth/user/refreshtoken").content(new ObjectMapper().writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        int n = resultContent.length();
        resultContent = resultContent.substring(n-8,n-2);
        String expected = "Bearer";
        Assert.assertEquals(expected,resultContent);
    }

    @Test
    @Order(4)
    public void SignOut() throws Exception {
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername("Test");
        userLogin.setPassword("Test");
        MvcResult result = mockMvc.perform(post("/api/auth/user/signin").content(new ObjectMapper().writeValueAsString(userLogin)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String[] list = resultContent.split(",")[5].split(":");
        String[] arr = list[1].split("\"");
        String accessToken = "Bearer "+arr[1];
        int userId = Integer.parseInt(resultContent.split(",")[0].split(":")[1]);
        result = mockMvc.perform(get("/api/auth/user/signout/"+userId).header(HttpHeaders.AUTHORIZATION,accessToken))
                .andExpect(status().isOk()).andReturn();
        Assert.assertTrue(true);
    }


}
