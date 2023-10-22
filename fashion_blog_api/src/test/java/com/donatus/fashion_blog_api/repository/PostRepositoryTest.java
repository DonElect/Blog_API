package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.model.entity.PostEntity;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import com.donatus.fashion_blog_api.model.enums.PostCategory;
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

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    UserEntity savedUser1;
    UserEntity savedUser2;
    PostEntity post1;
    PostEntity post2;
    PostEntity post3;

    PostEntity savedPost1;
    PostEntity savedPost2;
    PostEntity savedPost3;

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
                .build();

        UserEntity user2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Mary")
                .email("mary@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Mary")
                .location("Lagos State")
                .build();

        savedUser1 = userRepository.save(user1);
        savedUser2 = userRepository.save(user2);

        post1 = PostEntity.builder()
                .postAuthor("Benji")
                .postTitle("How to sow")
                .postBody("Sowing in the morning, sowing in the evening when the rain fails.")
                .category(PostCategory.SHOES)
                .build();

        post2 = PostEntity.builder()
                .postAuthor("Benji")
                .postTitle("Java Language")
                .postBody("Join me as i explain the world of a java programmer to you.")
                .category(PostCategory.BAGS)
                .build();

        post3 = PostEntity.builder()
                .postAuthor("Anthony")
                .postTitle("Agile")
                .postBody("What or who is an Agile developer?")
                .category(PostCategory.CLOTHES)
                .build();


        savedUser1.addPost(post1);
        savedPost1 = postRepository.save(post1);

        savedUser1.addPost(post2);
        savedPost2 = postRepository.save(post2);

        savedUser2.addPost(post3);
        savedPost3 = postRepository.save(post3);
    }


    @Test
    void findPostEntityByUserEntityUserIdAndPostId() {
        Optional<PostEntity> foundPost = postRepository
                .findPostEntityByUserEntityUserIdAndPostId(savedUser1.getUserId(), savedPost1.getPostId());

        Assertions.assertThat(foundPost.isPresent()).isTrue();
        Assertions.assertThat(foundPost.get().getPostId()).isGreaterThan(0);
    }

    @Test
    void findByUserEntityUserId() {
        Pageable pageable = PageRequest.of(0, 2);

        Slice<PostEntity> result = postRepository.findByUserEntityUserId(savedUser1.getUserId(), pageable);

        Assertions.assertThat(result.hasContent()).isTrue();
    }
}