package com.donatus.fashion_blog_api.entity;

import com.donatus.fashion_blog_api.enums.PostCategory;
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
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 45)
    private String postAuthor;

    @Column(nullable = false, length = 45)
    private String postTitle;

    @Column(nullable = false, length = 1000)
    private String postBody;

//    private Blob blob;
    @CreationTimestamp
    @Column(updatable = false, insertable = false)
    private LocalDateTime postDate;

    @UpdateTimestamp
    private LocalDateTime postUpdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostCategory category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
                                                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "admin_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "postEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostLikes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentsEntity> comments = new ArrayList<>();

    public void addComment(CommentsEntity comment){
        if (comments == null){
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setPostEntity(this);
    }

    public void addPostLike(PostLikes like){
        if (likes == null){
            likes = new ArrayList<>();
        }

        likes.add(like);
        like.setPostEntity(this);
    }
}
