package com.raj.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tid;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToMany(mappedBy = "tags")
    private Collection<Post> posts= new ArrayList<>();
    public Tag() {

    }

    public Tag(String name, Timestamp createdAt, Timestamp updatedAt, Collection<Post> posts) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.posts = posts;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Tag{" +
                ", name='" + name + '\'' +
                '}';
    }

    public Tag(String name, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Tag(String name) {
        this.name = name;
        createdAt = new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli());
        updatedAt = new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli());
    }
}
