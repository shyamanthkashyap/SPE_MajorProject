package com.example.project;

import com.example.project.entity.Answers;
import com.example.project.model.QuestionPostFactory;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AnswerControllerTests {
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
    public void GetAnswer() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/answer/list/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

    @Test
    @Rollback(value = false)
    public void GetMyAnswer() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/answer/listMyAnswer/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

//    @Test
//    @Rollback(value = false)
//    public void GiveLike() throws Exception {
//        Answers answers = new Answers();
//        answers.setAnswerBody("");
//        MvcResult result = mockMvc.perform(post("/api/answer/like/"+1).content(new ObjectMapper().writeValueAsString(q)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        String resultContent = result.getResponse().getContentAsString();
//        System.out.println(resultContent);
//        Assert.assertTrue(true);
//    }
}
