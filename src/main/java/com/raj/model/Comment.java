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
    private String comment;

    @ManyToOne
    private Post post_id;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost_id() {
        return post_id;
    }

    public void setPost_id(Post post_id) {
        this.post_id = post_id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
                ", comment='" + comment + '\'' +
                ", post_id=" + post_id.getPid() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Comment(String name, String email, String comment, Post post_id, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.email = email;
        this.comment = comment;
        this.post_id = post_id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Comment() {
    }
}