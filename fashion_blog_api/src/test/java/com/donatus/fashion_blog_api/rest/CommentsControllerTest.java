package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.dto.ImageDataResponseDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentRequestDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentResponseDTO;
import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.model.enums.PostCategory;
import com.donatus.fashion_blog_api.services.CommentServices;
import com.donatus.fashion_blog_api.services.PostServices;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentsController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disables spring security
@ExtendWith(MockitoExtension.class)
class CommentsControllerTest {

    @Autowired
    private CommentsController commentsController;

    @MockBean
    private CommentServices commentServices;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentResponseDTO commentResponseDTO;
    private CommentRequestDTO commentRequestDTO;
    private List<CommentResponseDTO> commentResponseDTOList;



    @BeforeEach
    void setUp() {
        commentRequestDTO = CommentRequestDTO.builder()
                .comment("Testing 1, 2, 3!")
                .build();

        commentResponseDTO = CommentResponseDTO.builder()
                .commentId(1L)
                .comment("Testing 1, 2, 3!")
                .commentDate(LocalDateTime.parse("2023-10-22T23:43:11.795966"))
                .commentUpdate(LocalDateTime.parse("2023-10-22T23:43:11.795966"))
                .build();

        commentResponseDTOList = new ArrayList<>(List.of(commentResponseDTO));

    }

    @Test
    void createComment() throws Exception {
        when(commentServices.makeComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(CommentRequestDTO.class))).thenReturn(commentResponseDTO);

        String content = objectMapper.writeValueAsString(commentRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/comments/{postId}/{userId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(commentsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"commentId\":1,\"comment\":\"Testing 1, 2, 3!\"," +
                                "\"commentDate\":[2023,10,22,23,43,11,795966000],\"commentUpdate\":[2023,10,22,23,43,11,795966000]}"));
    }

    @Test
    void showComment() throws Exception {
        when(commentServices.viewComment(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(commentResponseDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/comments/{userId}/{commentId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(commentsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"commentId\":1,\"comment\":\"Testing 1, 2, 3!\"," +
                                "\"commentDate\":[2023,10,22,23,43,11,795966000],\"commentUpdate\":[2023,10,22,23,43,11,795966000]}"));
    }

    @Test
    void updateComment() throws Exception {
        when(commentServices.editComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(CommentRequestDTO.class)))
                .thenReturn(commentResponseDTO);

        String content = objectMapper.writeValueAsString(commentRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/comments/{userId}/{commentId}",
                        1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(commentsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"commentId\":1,\"comment\":\"Testing 1, 2, 3!\",\"commentDate\":[2023,10,22,23,43,11,795966000],\"commentUpdate\":[2023,10,22,23,43,11,795966000]}"));
    }

    @Test
    void pagingComments() throws Exception {
        when(commentServices.pageComment(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(commentResponseDTOList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/comments/{postId}/{page_no}/{page_size}",
                        1L, 0, 2)
                .contentType(MediaType.APPLICATION_JSON);
        MockMvcBuilders.standaloneSetup(commentsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"commentId\":1,\"comment\":\"Testing 1, 2, 3!\"," +
                                "\"commentDate\":[2023,10,22,23,43,11,795966000],\"commentUpdate\":[2023,10,22,23,43,11,795966000]}]"));
    }

    @Test
    void pagingCommentLiker() throws Exception {
        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Mary")
                .email("mary@gmail.com")
                .userName("Mary")
                .location("Lagos State")
                .build();

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>(List.of(userResponseDTO));

        when(commentServices.pageCommentLiker(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(userResponseDTOList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/comments/likes/{commentId}/{page_no}/{page_size}",
                        1L, 0, 2)
                .contentType(MediaType.APPLICATION_JSON);
        MockMvcBuilders.standaloneSetup(commentsController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"userId\":1,\"firstName\":\"Jane\",\"lastName\":\"Mary\"," +
                                "\"email\":\"mary@gmail.com\",\"userName\":\"Mary\",\"location\":\"Lagos State\"}]"));
    }

    @Test
    void removeComment() throws Exception {
        doNothing().when(commentServices).deleteComment(Mockito.anyLong());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/comments/{commentId}",
                1L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(commentsController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}