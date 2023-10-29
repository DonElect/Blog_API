package com.donatus.blog_api.model.entity;

import com.donatus.blog_api.util.CustomIdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "blog_users")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "prod-generator")
    @GenericGenerator(name = "prod-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "US"),
            type = CustomIdGenerator.class)
    @Column(name = "user_id", length = 22)
    private String userId;

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
    private String location;

    @Column(nullable = false)
    private Boolean verified;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<PostEntity> postList = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<CommentsEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<PostLikes> postLikes = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<CommentLikes> commentLikes = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "role_id"))
    private Set<Roles> roles;


    public void addRoles(Roles role){
        if (roles == null){
            roles = new HashSet<>();
        }

        roles.add(role);
    }

    public void addPost(PostEntity post){
        if (postList == null){
            postList = new HashSet<>();
        }

        postList.add(post);
        post.setUserEntity(this);
    }

    public void addComment(CommentsEntity comment){
        if (comments == null){
            comments = new HashSet<>();
        }

        comments.add(comment);
        comment.setUserEntity(this);
    }

    public void addPostLike(PostLikes like){
        if (postLikes == null){
            postLikes = new HashSet<>();
        }

        postLikes.add(like);
        like.setUserEntity(this);
    }

    public void addCommentLike(CommentLikes like){
        if (commentLikes == null){
            commentLikes = new HashSet<>();
        }

        commentLikes.add(like);
        like.setUserEntity(this);
    }
}
