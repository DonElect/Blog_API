package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.services.LikeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/likes")
public class LikesController {

    private final LikeServices likeServices;

    @GetMapping("/posts/{userId}/{postId}")
    public ResponseEntity<Boolean> likePost(@PathVariable Long userId,
                                            @PathVariable Long postId){
        return ResponseEntity.ok(likeServices.likePost(userId, postId));
    }

    @GetMapping("/comments/{userId}/{commentId}")
    public ResponseEntity<Boolean> likeComment(@PathVariable Long userId,
                                               @PathVariable Long commentId){
        return ResponseEntity.ok(likeServices.likeComment(userId, commentId));
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
