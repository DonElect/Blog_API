package com.donatus.blog_api.services.implimenation;

import com.donatus.blog_api.dto.comment.CommentRequestDTO;
import com.donatus.blog_api.dto.comment.CommentResponseDTO;
import com.donatus.blog_api.dto.user.UserResponseDTO;
import com.donatus.blog_api.exception.CommentNotFoundException;
import com.donatus.blog_api.exception.PostNotFoundException;
import com.donatus.blog_api.exception.UserNotFoundException;
import com.donatus.blog_api.model.entity.CommentsEntity;
import com.donatus.blog_api.model.entity.PostEntity;
import com.donatus.blog_api.model.entity.UserEntity;
import com.donatus.blog_api.repository.CommentRepository;
import com.donatus.blog_api.repository.PostRepository;
import com.donatus.blog_api.repository.UserRepository;
import com.donatus.blog_api.services.CommentServices;
import com.donatus.blog_api.util.MyDTOMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentServices {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final MyDTOMapper myDTOMapper = new MyDTOMapper();

    @Override
    public CommentResponseDTO makeComment(Long postId, CommentRequestDTO comment) {
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        PostEntity post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No post with id: "+postId));

        UserEntity user = userRepo.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user with email "+email));

        CommentsEntity newComment = mapper.map(comment, CommentsEntity.class);

        post.addComment(newComment);
        user.addComment(newComment);

        CommentsEntity saveComment = commentRepo.save(newComment);

        return mapper.map(saveComment, CommentResponseDTO.class);
    }

    @Override
    public CommentResponseDTO viewComment(Long commentId) {
        CommentsEntity comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));

        return mapper.map(comment, CommentResponseDTO.class);
    }

    @Override
    public CommentResponseDTO editComment(Long commentId, CommentRequestDTO comment) {
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepo.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user with email: "+email+" found."));

        CommentsEntity oldComment = commentRepo.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));

        mapper.map(comment, oldComment);

        user.addComment(oldComment);

        CommentsEntity newComment = commentRepo.save(oldComment);

        return mapper.map(newComment, CommentResponseDTO.class);
    }

    @Override
    public List<CommentResponseDTO> pageComment(Long postId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("commentDate"));

        Slice<CommentsEntity> result = commentRepo.findCommentsEntityByPostEntityPostId(postId, pageable);

        return result.isEmpty() ? new ArrayList<>() : myDTOMapper.mapCommentResponse(result.getContent());
    }

    @Override
    public List<UserResponseDTO> pageCommentLiker(Long commentId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("first_name"));

        Slice<UserEntity> result = userRepo.findCommentLiker(commentId, pageable);

        return result.isEmpty() ? new ArrayList<>() : myDTOMapper.mapUserResponse(result.getContent());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}
