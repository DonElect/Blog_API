package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.dto.ImageDataResponseDTO;
import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.services.PostServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsController {
    private final PostServices postServices;


    @PostMapping("/{adminId}")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody @Valid PostRequestDTO postRequestDTO,
                                                      @PathVariable Long adminId) {
        return new ResponseEntity<>(postServices.makePost(adminId, postRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/images/{postId}")
    public ResponseEntity<ImageDataResponseDTO> savePostImage(@RequestParam("image") MultipartFile file,
                                                              @PathVariable Long postId) throws IOException {
       return new ResponseEntity<>(postServices.uploadPostImage(file, postId), HttpStatus.CREATED);
    }

    @PutMapping("/{adminId}/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody @Valid PostRequestDTO postRequestDTO,
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

        return ResponseEntity.status(202).body("Post with ID: "+postId+" deleted successfully.");
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId){
        postServices.deleteAPostImage(imageId);

        return ResponseEntity.status(202).body("Post with ID: "+imageId+" deleted successfully.");
    }

}
