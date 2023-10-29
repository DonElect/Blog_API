package com.donatus.blog_api.rest;

import com.donatus.blog_api.dto.ImageDataResponseDTO;
import com.donatus.blog_api.dto.post.PostRequestDTO;
import com.donatus.blog_api.dto.post.PostResponseDTO;
import com.donatus.blog_api.dto.user.UserResponseDTO;
import com.donatus.blog_api.services.PostServices;
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


    @PostMapping("")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody @Valid PostRequestDTO postRequestDTO) {
        return new ResponseEntity<>(postServices.makePost(postRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/images/{postId}")
    public ResponseEntity<ImageDataResponseDTO> savePostImage(@RequestParam("image") MultipartFile file,
                                                              @PathVariable Long postId) throws IOException {
       return new ResponseEntity<>(postServices.uploadPostImage(file, postId), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody @Valid PostRequestDTO postRequestDTO,
                                                      @PathVariable Long postId){
        return ResponseEntity.ok(postServices.editPost(postId, postRequestDTO));
    }

    @GetMapping("/page_no/{pageNo}/page_size/{pageSize}")
    public ResponseEntity<List<PostResponseDTO>> pagingPost(@PathVariable Integer pageNo,
                                                            @PathVariable Integer pageSize){
        return ResponseEntity.ok(postServices.pagePost(pageNo, pageSize));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> viewPost(@PathVariable Long postId){
        return ResponseEntity.ok(postServices.viewPost(postId));
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
