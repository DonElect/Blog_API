package com.donatus.fashion_blog_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blog_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(length = 25, nullable = false)
    private String firstName;

    @Column(length = 25)
    private String lastName;

    @Column(length = 25, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(length = 15, nullable = false)
    private String userName;

    @Column(length = 45, nullable = false)
    private String address;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostEntity> postList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentsEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostLikes> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentLikes> commentLikes = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "role_id"))
    private List<Roles> roles = new ArrayList<>();


    public void addPost(PostEntity post){
        if (postList == null){
            postList = new ArrayList<>();
        }

        postList.add(post);
        post.setUserEntity(this);
    }

    public void addComment(CommentsEntity comment){
        if (comments == null){
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setUserEntity(this);
    }

    public void addPostLike(PostLikes like){
        if (postLikes == null){
            postLikes = new ArrayList<>();
        }

        postLikes.add(like);
        like.setUserEntity(this);
    }

    public void addCommentLike(CommentLikes like){
        if (commentLikes == null){
            commentLikes = new ArrayList<>();
        }

        commentLikes.add(like);
        like.setUserEntity(this);
    }
}
