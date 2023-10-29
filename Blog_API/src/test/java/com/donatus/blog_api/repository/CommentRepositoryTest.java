package com.donatus.blog_api.repository;

import com.donatus.blog_api.model.entity.CommentsEntity;
import com.donatus.blog_api.model.entity.PostEntity;
import com.donatus.blog_api.model.entity.UserEntity;
import com.donatus.blog_api.model.enums.PostCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CommentRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    private PostEntity savedPost1;


    @BeforeEach
    void setUp() {
        UserEntity user1 = UserEntity.builder()
                .firstName("Paul")
                .lastName("Mike")
                .email("mike@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Miko")
                .location("Edo State")
                .verified(true)
                .build();

        UserEntity savedUser1 = userRepository.save(user1);

        PostEntity post1 = PostEntity.builder()
                .postAuthor("Benji")
                .postTitle("How to sow")
                .postBody("Sowing in the morning, sowing in the evening when the rain fails.")
                .category(PostCategory.SHOES)
                .build();

        // Adding post to an existing user
        savedUser1.addPost(post1);
        savedPost1 = postRepository.save(post1);

        CommentsEntity comment1 = CommentsEntity.builder()
                .comment("Nice!")
                .build();

        CommentsEntity comment2 = CommentsEntity.builder()
                .comment("That looks great!")
                .build();

        // Adding comments to an existing post
        savedPost1.addComment(comment1);
        comment1.setUserEntity(savedUser1);
        CommentsEntity savedComment1 = commentRepository.save(comment1);

        savedPost1.addComment(comment2);
    }


    @Test
    void findCommentsEntityByPostEntityPostId() {
        Pageable pageable = PageRequest.of(0, 2);

        Slice<CommentsEntity> result = commentRepository
                .findCommentsEntityByPostEntityPostId(savedPost1.getPostId(), pageable);

        Assertions.assertThat(result.hasContent()).isTrue();
    }
}