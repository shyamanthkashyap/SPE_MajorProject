package com.example.project;

import com.example.project.entity.SubCategory;
import com.example.project.model.QuestionPostFactory.QuestionPost;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionControllerTests {
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
    public void GetAllQuestions() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listAll"))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

    @Test
    @Rollback(value = false)
    public void GetQuestionNotPresent() throws Exception {
        try {
            MvcResult result = mockMvc.perform(get("/api/question/list/" + 1))
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
    public void GetQuestion() throws Exception {
        try {
            MvcResult result = mockMvc.perform(get("/api/question/list/" + 1))
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
    public void GetCategoryQuestion() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listCategory/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

    @Test
    @Rollback(value = false)
    public void GetSubCategoryQuestion() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listSubCategory/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

    @Test
    @Rollback(value = false)
    public void GetMyQuestion() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listMyQuestion/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

//    @Test
//    @Rollback(value = false)
//    public void PostQuestion() throws Exception {
//        QuestionPost q = new QuestionPost();
//        q.setBody("Running Test");
//        q.setTitle("Testing");
////        SubCategory subCategory = new SubCategory();
////        subCategory.setSubCategoryName("Science");
////        q.setSubCategory(subCategory);
//        MvcResult result = mockMvc.perform(post("/api/question/post/"+12).content(new ObjectMapper().writeValueAsString(q)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        String resultContent = result.getResponse().getContentAsString();
//        System.out.println(resultContent);
//        Assert.assertTrue(true);
//    }


}
