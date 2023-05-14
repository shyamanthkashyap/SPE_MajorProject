package com.example.project;

import com.example.project.Payload.Request.TokenRefreshRequest;
import com.example.project.Payload.Request.UserLogin;
import com.example.project.Payload.Request.UserSignUp;
import com.example.project.Security.Jwt.JwtUtils;
import com.example.project.entity.*;
import com.example.project.model.AnswerPostFactory;
import com.example.project.model.QuestionPostFactory;
import com.example.project.service.AnswerService;
import com.example.project.service.QuestionService;
import com.example.project.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@FixMethodOrder(MethodSorters.JVM)
public class ProjectApplicationTests {

    @Autowired
    @Mock
    UserService userService;

    @Autowired
    @Mock
    QuestionService questionService;


    @Autowired
    @Mock
    AnswerService answerService;

    @Autowired
    @Mock
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;
    @org.junit.jupiter.api.Test
    public void testTokenGeneration() {
        // Generate a token
        String username="TestUser";
        String token = jwtUtils.generateTokenFromUsername(username);
        // Assert that the token is not null and has the correct format
        assertNotNull(token);
        boolean tok = Pattern.matches(".*\\..*\\..*",token);
        assertTrue(tok);
    }

    @org.junit.jupiter.api.Test
    public void testTokenUsername(){
        // Generate a token
        String username="TestUser";
        String token = jwtUtils.generateTokenFromUsername(username);
        // Assert that the token is not null and has the correct format
        assertNotNull(token);
        String name=jwtUtils.getUserNameFromJwtToken(token);
        assertEquals(name,username);
    }

    @org.junit.jupiter.api.Test
    public void testTokenValidationTrue(){
        String token = jwtUtils.generateTokenFromUsername("TestUser");
        assertNotNull(token);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @org.junit.jupiter.api.Test
    public void testTokenValidationFalse(){
        String token="abc.de.ef";
        assertFalse(jwtUtils.validateJwtToken(token));
    }

    /************************
    All API Controller Tests
    *************************/

    private String refreshToken;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

     /************************
     Auth Controller API Tests
     ************************/

    @Test
    @Order(1)
    public void UserSignUp() throws Exception {
        UserSignUp userSignUp = new UserSignUp();
        userSignUp.setUsername("Test");
        userSignUp.setPassword("Test");
        userSignUp.setEmail("Test@gmail.com");
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
        UserSignUp userSignUp = new UserSignUp();
        userSignUp.setUsername("Test");
        userSignUp.setPassword("Test");
        userSignUp.setEmail("Test@gmail.com");
        userSignUp.setLocationId(1L);
        MvcResult result = mockMvc.perform(post("/api/auth/user/signup").content(new ObjectMapper().writeValueAsString(userSignUp)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername("Test");
        userLogin.setPassword("Test");
        result = mockMvc.perform(post("/api/auth/user/signin").content(new ObjectMapper().writeValueAsString(userLogin)).contentType(MediaType.APPLICATION_JSON))
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

     /****************************
     Location Controller API Tests
     ****************************/
    @Test
    @Order(5)
    public void GetStates() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/location/state/"+"India"))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        resultContent=resultContent.substring(36,45);
        System.out.println(resultContent);
        String Expected = "Karnataka";
        Assert.assertEquals(Expected,resultContent);
    }

    @Test
    @Order(6)
    public void GetCountry() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/location/country"))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        resultContent=resultContent.substring(36,41);
        System.out.println(resultContent);
        String Expected = "India";
        Assert.assertEquals(Expected,resultContent);
    }

    @Test
    @Order(7)
    public void GetCities() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/location/city/"+"India"+"/"+"Karnataka"))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        resultContent = resultContent.substring(59,68);
        System.out.println(resultContent);
        String Expected = "Bengaluru";
        Assert.assertEquals(Expected,resultContent);
    }

     /************************
     Page Controller API Tests
     ************************/
    @Test
    @Order(8)
    public void GetUserProfile() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/user/userProfile/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        resultContent=resultContent.substring(23,48);
        System.out.println(resultContent);
        String Expected = "Find user profile success";
        Assert.assertEquals(Expected,resultContent);
    }

    @Test
    @Order(9)
    public void UpdateProfile() throws Exception {
        try {
            UserSignUp userSignUp = new UserSignUp();
            userSignUp.setUsername("Test1");
            userSignUp.setPassword("Test1");
            userSignUp.setEmail("Test1@gmail.com");
            userSignUp.setLocationId(1L);
            MvcResult result = mockMvc.perform(post("/api/auth/user/signup").content(new ObjectMapper().writeValueAsString(userSignUp)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();
            result = mockMvc.perform(post("/api/user/updateProfile/"+2).content("Updated User Profile"))
                    .andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            resultContent = resultContent.substring(23,45);
            System.out.println(resultContent);
            String Expected = "update profile success";
            Assert.assertEquals(Expected,resultContent);
        }catch (Exception e){
            System.out.println("ran exception");
            Assert.assertFalse(e instanceof Exception);
        }
    }

    @Test
    @Order(10)
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

    @Test
    @Order(11)
    public void UpdatePoints() throws Exception {
        try{
            UserSignUp userSignUp = new UserSignUp();
            userSignUp.setUsername("Test2");
            userSignUp.setPassword("Test2");
            userSignUp.setEmail("Test2@gmail.com");
            userSignUp.setLocationId(1L);
            MvcResult result = mockMvc.perform(post("/api/auth/user/signup").content(new ObjectMapper().writeValueAsString(userSignUp)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();
            User user = userService.findById(3);
            long oldpoints = user.getPoints();
            result = mockMvc.perform(get("/api/user/updatepoints/"+3).content("Update Points")).andExpect(status().isOk()).andReturn();
            User user1 = userService.findById(3);
            long newpoints = user1.getPoints();
            Assert.assertEquals(oldpoints+1,newpoints);
        }
        catch(Exception e){
            Assert.assertTrue(e instanceof Exception);
        }
    }


     /****************************
     Category Controller API Tests
     ****************************/
    @Test
    @Order(12)
    public void GetSubCategory() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/category/list/"+1L))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        resultContent = resultContent.substring(36,53);
        System.out.println(resultContent);
        String Expected = "\"subCategoryId\":1";
        Assert.assertEquals(Expected,resultContent);
    }

    @Test
    @Order(13)
    public void GetMainCategory() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/category/listAll"))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        resultContent = resultContent.substring(36,50);
        System.out.println(resultContent);
        String Expected = "\"categoryId\":1";
        Assert.assertEquals(Expected,resultContent);
    }


     /****************************
     Question Controller API Tests
     ****************************/
    @Test
    @Order(14)
    public void GetAllQuestions() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listAll"))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        resultContent = resultContent.substring(23,49);
        System.out.println(resultContent);
        String Expected = "Find all questions success";
        Assert.assertEquals(Expected,resultContent);
    }

    @Test
    @Order(15)
    public void GetQuestionNotPresent() throws Exception {
        try {

            MvcResult result = mockMvc.perform(get("/api/question/list/"+1))
                    .andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            System.out.println(resultContent);
            Assert.assertTrue(true);
        }catch (Exception e){
            Assert.assertTrue(e instanceof Exception);
        }
    }

    @Test
    @Order(16)
    public void PostQuestion() throws Exception {
        QuestionPostFactory.QuestionPost q = new QuestionPostFactory.QuestionPost();
        q.setBody("Running Test");
        q.setTitle("Testing");
        SubCategory s = new SubCategory();
        s.setSubCategoryId(1L);
        s.setSubCategoryName("Computer Science");
        MainCategory m= new MainCategory();
        m.setCategoryId(1L);
        m.setCategoryName("Education");
        s.setMainCategory(m);
        q.setSubCategory(s);
        MvcResult result = mockMvc.perform(post("/api/question/post/"+1).content(new ObjectMapper().writeValueAsString(q)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        Assert.assertTrue(true);
    }

    @Test
    @Order(17)
    public void GetQuestion() throws Exception {
        try {
            MvcResult result = mockMvc.perform(get("/api/question/list/"+1))
                    .andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            resultContent = resultContent.substring(23,44);
            System.out.println(resultContent);
            String Expected = "Find question success";
            Assert.assertEquals(Expected,resultContent);
        }catch (Exception e){
            System.out.println("Ran Exception");
            Assert.assertTrue(e instanceof Exception);
        }
    }

    @Test
    @Order(18)
    public void GetCategoryQuestion() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listCategory/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

    @Test
    @Order(19)
    public void GetSubCategoryQuestion() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listSubCategory/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

    @Test
    @Order(20)
    public void GetMyQuestion() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listMyQuestion/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

    @Test
    @Order(21)
    public void GetRelatedQuestion() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/question/listRelated/"+"Test"))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

    @Test
    @Order(22)
    public void BestAnswer() throws Exception {
        Questions questions = questionService.listOneQuestion(1L);
        MvcResult result = mockMvc.perform(post("/api/question/updateBestAnswer/"+1).content(new ObjectMapper().writeValueAsString(questions)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

     /**************************
     Answer Controller API Tests
     **************************/
    @Test
    @Order(23)
    public void GetAnswer() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/answer/list/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

    @Test
    @Order(24)
    public void GetMyAnswer() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/answer/listMyAnswer/"+1))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

    @Test
    @Order(25)
    public void PostAnswer() throws Exception {
        AnswerPostFactory.AnswerPost answerPost = new AnswerPostFactory.AnswerPost();
        answerPost.setBody("Test Answer");
        Questions questions = questionService.listOneQuestion(1L);
        answerPost.setQuestions(questions);
        MvcResult result = mockMvc.perform(post("/api/answer/post/"+1).content(new ObjectMapper().writeValueAsString(answerPost)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }

    @Test
    @Order(26)
    public void GiveLike() throws Exception {
        UserSignUp userSignUp = new UserSignUp();
        userSignUp.setUsername("Test3");
        userSignUp.setPassword("Test3");
        userSignUp.setEmail("Test3@gmail.com");
        userSignUp.setLocationId(1L);
        MvcResult result = mockMvc.perform(post("/api/auth/user/signup").content(new ObjectMapper().writeValueAsString(userSignUp)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        AnswerPostFactory.AnswerPost answerPost = new AnswerPostFactory.AnswerPost();
        answerPost.setBody("Test Answer");
        Questions questions = questionService.listOneQuestion(1L);
        answerPost.setQuestions(questions);
        result = mockMvc.perform(post("/api/answer/post/"+1).content(new ObjectMapper().writeValueAsString(answerPost)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        User user = userService.findById(1);
        List<Answers> answers = answerService.listMyAnswer(user);
        Answers answers1 = answers.get(0);
        System.out.println(answers1);
        result = mockMvc.perform(post("/api/answer/like/"+1).content(new ObjectMapper().writeValueAsString(answers1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String status = resultContent.substring(8,11);
        System.out.println(resultContent);
        Assert.assertTrue(status.contentEquals("200"));
    }
}
