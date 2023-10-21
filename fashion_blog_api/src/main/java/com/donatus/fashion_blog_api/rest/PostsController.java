package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.services.PostServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsController {
    private final PostServices postServices;


    @PostMapping("/{adminId}")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO,
                                                      @PathVariable Long adminId){
        return ResponseEntity.ok(postServices.makePost(adminId, postRequestDTO));
    }

    @PutMapping("/{adminId}/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody PostRequestDTO postRequestDTO,
                                                      @PathVariable Long adminId,
                                                      @PathVariable Long postId){
        return ResponseEntity.ok(postServices.editPost(adminId, postId, postRequestDTO));
    }

    @GetMapping("/{adminId}/page_no/{pageNo}/page_size/{pageSize}")
    public ResponseEntity<List<PostResponseDTO>> pagingPost(@PathVariable Long adminId,
                                                            @PathVariable Integer pageNo,
                                                            @PathVariable Integer pageSize){
        return ResponseEntity.ok(postServices.pagePost(adminId, pageNo, pageSize));
    }

    @GetMapping("/{adminId}/{postId}")
    public ResponseEntity<PostResponseDTO> viewPost(@PathVariable Long adminId,
                                                    @PathVariable Long postId){
        return ResponseEntity.ok(postServices.viewPost(adminId, postId));
    }

    @GetMapping("/likes/{postId}/page_no/{pageNo}/page_size/{pageSize}")
    public ResponseEntity<List<UserResponseDTO>> pagingPostLiker(@PathVariable Long postId,
                                                                 @PathVariable Integer pageNo,
                                                                 @PathVariable Integer pageSize){
        return ResponseEntity.ok(postServices.pagePostLiker(postId, pageNo, pageSize));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> removePost(@PathVariable Long postId){
        postServices.deletePost(postId);

        return ResponseEntity.ok("Post with ID: "+postId+" deleted successfully.");
    }

}
