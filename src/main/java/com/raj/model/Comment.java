package com.raj.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cid;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "comment",
            nullable = false
    )
    private String commentMsg;

    @ManyToOne
    private Post postId;

    @Column(
            name = "created_at",
            nullable = false
    )
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = Timestamp.valueOf(createdAt);
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + commentMsg + '\'' +
                ", post_id=" + postId.getPid() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Comment(String name, String email, String comment, Post post_id, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.email = email;
        this.commentMsg = comment;
        this.postId = post_id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Comment() {
    }
}