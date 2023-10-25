package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.model.enums.PostCategory;
import com.donatus.fashion_blog_api.services.PostServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostsController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disables spring security
@ExtendWith(MockitoExtension.class)
class PostsControllerTest {

    @Autowired
    private PostsController postsController;

    @MockBean
    private PostServices postServices;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private PostResponseDTO postResponseDTO;
    private PostRequestDTO postRequestDTO;
    private List<PostResponseDTO> postResponseDTOList;


    @BeforeEach
    void setUp() {
        postRequestDTO = PostRequestDTO.builder()
                .postAuthor("Dona")
                .postCategory("SHOES")
                .postTitle("What is your best shoe")
                .postBody("Vote on your best shoe.")
                .build();

        postResponseDTO = PostResponseDTO.builder()
                .postId(1L)
                .postAuthor("Dona")
                .category(PostCategory.valueOf("SHOES"))
                .postTitle("What is your best shoe")
                .postBody("Vote on your best shoe.")
                .postDate(LocalDateTime.parse("2023-10-22T23:43:11.795966"))
                .postUpdate(LocalDateTime.parse("2023-10-22T23:43:11.795966"))
                .imageData(new ArrayList<>())
                .build();

        postResponseDTOList = new ArrayList<>(List.of(postResponseDTO));

    }

    @Test
    void createPost() throws Exception {
        when(postServices.makePost(Mockito.anyLong(), Mockito.any(PostRequestDTO.class))).thenReturn(postResponseDTO);

        String content = objectMapper.writeValueAsString(postRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/posts/{adminId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(postsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"postId\":1,\"postAuthor\":\"Dona\",\"postTitle\":\"What is your best shoe\",\"postBody\":" +
                                "\"Vote on your best shoe.\",\"postDate\":[2023,10,22,23,43,11,795966000],\"postUpdate\":[2023,10,22,23,43,11,795966000]," +
                                "\"category\":\"SHOES\",\"imageData\":[]}"));
    }

    @Test
    void savePostImage() throws Exception {
        byte[] content = objectMapper.writeValueAsBytes(MultipartFile.class);

        MockMultipartFile file
                = new MockMultipartFile(
                "image",
                "myImage.png",
                MediaType.TEXT_PLAIN_VALUE,
                content
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/api/v1/posts/images/{postId}", 1L).file(file))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePost() throws Exception {
        when(postServices.editPost(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(PostRequestDTO.class)))
                .thenReturn(postResponseDTO);

        String content = objectMapper.writeValueAsString(postRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/posts/{adminId}/{postId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(postsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"postId\":1,\"postAuthor\":\"Dona\",\"postTitle\":\"What is your best shoe\",\"postBody\":" +
                                "\"Vote on your best shoe.\",\"postDate\":[2023,10,22,23,43,11,795966000],\"postUpdate\":[2023,10,22,23,43,11,795966000]," +
                                "\"category\":\"SHOES\",\"imageData\":[]}"));
    }

    @Test
    void pagingPost() throws Exception {
        when(postServices.pagePost(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(postResponseDTOList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts/{adminId}/page_no/{pageNo}/page_size/{pageSize}",
                        1L, 0, 2)
                .contentType(MediaType.APPLICATION_JSON);
        MockMvcBuilders.standaloneSetup(postsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"postId\":1,\"postAuthor\":\"Dona\",\"postTitle\":\"What is your best shoe\",\"postBody\":" +
                                "\"Vote on your best shoe.\",\"postDate\":[2023,10,22,23,43,11,795966000],\"postUpdate\":[2023,10,22,23,43,11,795966000]," +
                                "\"category\":\"SHOES\",\"imageData\":[]}]"));
    }

    @Test
    void viewPost() throws Exception {
        when(postServices.viewPost(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(postResponseDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts/{adminId}/{postId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(postsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"postId\":1,\"postAuthor\":\"Dona\",\"postTitle\":\"What is your best shoe\",\"postBody\":" +
                                "\"Vote on your best shoe.\",\"postDate\":[2023,10,22,23,43,11,795966000],\"postUpdate\":[2023,10,22,23,43,11,795966000]," +
                                "\"category\":\"SHOES\",\"imageData\":[]}"));
    }

    @Test
    void pagingPostLiker() throws Exception {
        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Mary")
                .email("mary@gmail.com")
                .userName("Mary")
                .location("Lagos State")
                .build();

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>(List.of(userResponseDTO));

        when(postServices.pagePostLiker(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(userResponseDTOList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts/likes/{postId}/page_no/{pageNo}/page_size/{pageSize}",
                        1L, 0, 2)
                .contentType(MediaType.APPLICATION_JSON);
        MockMvcBuilders.standaloneSetup(postsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"userId\":1,\"firstName\":\"Jane\",\"lastName\":\"Mary\"," +
                                "\"email\":\"mary@gmail.com\",\"userName\":\"Mary\",\"location\":\"Lagos State\"}]"));
    }

    @Test
    void removePost() throws Exception {
        doNothing().when(postServices).deletePost(Mockito.anyLong());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/posts/{postId}",
                1L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(postsController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void deleteImage() throws Exception {
        doNothing().when(postServices).deleteAPostImage(Mockito.anyLong());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/posts/images/{imageId}",
                1L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(postsController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}