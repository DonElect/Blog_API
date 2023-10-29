package com.donatus.blog_api.rest;

import com.donatus.blog_api.services.LikeServices;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LikesController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disables spring security
@ExtendWith(MockitoExtension.class)
class LikesControllerTest {

    @Autowired
    private LikesController likesController;

    @MockBean
    private LikeServices likeServices;


    @BeforeEach
    void setUp() {
    }

    @Test
    void likePost() throws Exception {
        when(likeServices.likePost(Mockito.anyLong()))
                .thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/likes/posts/{postId}",
                         1L)
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(likesController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void likeComment() throws Exception {
        when(likeServices.likeComment(Mockito.anyLong()))
                .thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/likes/comments/{commentId}",
                         1L)
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(likesController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void postLikesCount() throws Exception {
        Long totalPostLike = 100L;
        when(likeServices.countPostLikes(Mockito.anyLong()))
                .thenReturn(totalPostLike);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/likes/posts/count/{postId}",
                        1L, 1L)
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(likesController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("100"));
    }

    @Test
    void commentLikesCount() throws Exception {
        Long totalPostLike = 120L;
        when(likeServices.countCommentLikes(Mockito.anyLong()))
                .thenReturn(totalPostLike);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/likes/comments/count/{commentId}",
                        1L, 1L)
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(likesController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("120"));
    }
}