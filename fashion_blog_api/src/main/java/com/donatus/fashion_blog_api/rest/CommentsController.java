package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.dto.comment.CommentRequestDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.services.CommentServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments/")
public class CommentsController {
    private final CommentServices commentServices;

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody @Valid CommentRequestDTO comment,
                                                            @PathVariable Long postId,
                                                            @PathVariable Long userId){
        return new ResponseEntity<>(commentServices.makeComment(postId, userId, comment), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/{commentId}")
    public ResponseEntity<CommentResponseDTO> showComment(@PathVariable Long userId,
                                                          @PathVariable Long commentId){
        return ResponseEntity.ok(commentServices.viewComment(userId, commentId));
    }

    @PutMapping("/{userId}/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@RequestBody @Valid CommentRequestDTO comment,
                                                            @PathVariable Long userId,
                                                            @PathVariable Long commentId){
        return ResponseEntity.ok(commentServices.editComment(userId, commentId, comment));
    }

    @GetMapping("/{postId}/{page_no}/{page_size}")
    public ResponseEntity<List<CommentResponseDTO>> pagingComments(@PathVariable Long postId,
                                                                   @PathVariable Integer page_no,
                                                                   @PathVariable Integer page_size){
        return ResponseEntity.ok(commentServices.pageComment(postId, page_no, page_size));
    }

    @GetMapping("/likes/{commentId}/{page_no}/{page_size}")
    public ResponseEntity<List<UserResponseDTO>> pagingCommentLiker(@PathVariable Long commentId,
                                                                    @PathVariable Integer page_no,
                                                                    @PathVariable Integer page_size){
        return ResponseEntity.ok(commentServices.pageCommentLiker(commentId, page_no, page_size));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> removeComment(@PathVariable Long commentId){
        commentServices.deleteComment(commentId);
        return ResponseEntity.status(202).body("Comment with ID: "+commentId+" deleted successfully.");
    }
}
