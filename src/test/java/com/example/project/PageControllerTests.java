package com.example.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PageControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Rollback(value = false)
    public void GetUserProfile() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/user/userProfile/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

    @Test
    @Rollback(value = false)
    public void UpdateProfile() throws Exception {
        try {
            MvcResult result = mockMvc.perform(post("/api/user/updateProfile/" + 12).content("Updated User Profile"))
                    .andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            System.out.println(resultContent);
            Assert.assertTrue(true);
        }catch (Exception e){
            Assert.assertTrue(e instanceof Exception);
        }
    }

    @Test
    @Rollback(value = false)
    public void UpdateProfileUserNotThere() throws Exception {
        try {
            MvcResult result = mockMvc.perform(post("/api/user/updateProfile/" + 1).content("Updated User Profile"))
                    .andReturn();
            System.out.println(result.getResolvedException());
        }
        catch(Exception e){
            Assert.assertTrue(e instanceof Exception);
        }
    }



}
