package com.donatus.fashion_blog_api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 1000)
    private String comment;

    @CreationTimestamp
    @Column(updatable = false, insertable = false)
    private LocalDateTime commentDate;

    @UpdateTimestamp
    private LocalDateTime commentUpdate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                          CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
                                                CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    @OneToMany(mappedBy = "commentsEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentLikes> likes = new ArrayList<>();


    public void addCommentLikes(CommentLikes like){
        if (likes == null){
            likes = new ArrayList<>();
        }

        likes.add(like);

        like.setCommentsEntity(this);
    }
}
