package com.donatus.blog_api.rest;

import com.donatus.blog_api.services.LikeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/likes")
public class LikesController {

    private final LikeServices likeServices;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<Boolean> likePost(@PathVariable Long postId){
        return ResponseEntity.ok(likeServices.likePost(postId));
    }

    @PostMapping("/comments/{commentId}")
    public ResponseEntity<Boolean> likeComment(@PathVariable Long commentId){
        return ResponseEntity.ok(likeServices.likeComment(commentId));
    }

    @GetMapping("/posts/count/{postId}")
    public ResponseEntity<Long> postLikesCount(@PathVariable Long postId){
        return ResponseEntity.ok(likeServices.countPostLikes(postId));
    }

    @GetMapping("/comments/count/{commentId}")
    public ResponseEntity<Long> commentLikesCount(@PathVariable Long commentId){
        return ResponseEntity.ok(likeServices.countCommentLikes(commentId));
    }
}
