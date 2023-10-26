package com.donatus.fashion_blog_api.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "comments")
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 1000)
    private String comment;

    @CreationTimestamp
    @Column(updatable = false)
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
    private Set<CommentLikes> likes = new HashSet<>();


    public void addCommentLikes(CommentLikes like){
        if (likes == null){
            likes = new HashSet<>();
        }

        likes.add(like);

        like.setCommentsEntity(this);
    }
}
