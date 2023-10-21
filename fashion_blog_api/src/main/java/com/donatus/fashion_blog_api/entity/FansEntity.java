//package com.donatus.fashion_blog_api.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "fans")
//public class FansEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long fanId;
//
//    @Column(nullable = false, length = 25)
//    private String firstName;
//
//    @Column(length = 25)
//    private String lastName;
//
//    @Column(length = 25, nullable = false)
//    private String email;
//
//    @Column(length = 60, nullable = false)
//    private String password;
//
//    @Transient
//    private String confirmPassword;
//
//    @Column(length = 15, nullable = false)
//    private String userName;
//
//    @Column(length = 25)
//    private String location;
//
//    @OneToMany(mappedBy = "fansEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<CommentsEntity> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "fansEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<PostLikes> postLikes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "fansEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<CommentLikes> commentLikes = new ArrayList<>();
//
//
//    public void addPostLike(PostLikes like){
//        if (postLikes == null){
//            postLikes = new ArrayList<>();
//        }
//
//        postLikes.add(like);
//        like.setFansEntity(this);
//    }
//
//    public void addCommentLike(CommentLikes like){
//        if (commentLikes == null){
//            commentLikes = new ArrayList<>();
//        }
//
//        commentLikes.add(like);
//        like.setFansEntity(this);
//    }
//
//    public void addComment(CommentsEntity comment){
//        if (comments == null){
//            comments = new ArrayList<>();
//        }
//
//        comments.add(comment);
//        comment.setFansEntity(this);
//    }
//
//}
