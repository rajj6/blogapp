package com.raj.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "posts")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pid;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "excerpt",
            nullable = false
    )
    private String excerpt;

    @Column(
            name = "content",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String content;

    @Transient
    private String tagsString;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "post_id")
    private Collection<Comment> comments = new ArrayList<>();

    @ManyToMany
    private Collection<Tag> tags = new ArrayList<>();

    @Column(name="published_at")
    private Timestamp publishedAt;

    @Column(name = "is_published",
        columnDefinition = "boolean default false"
    )
    private boolean isPublished;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Post() {

    }

    public Post(String title, String excerpt, String content, User author, Timestamp publishedAt, boolean isPublished, Timestamp createdAt, Timestamp updatedAt) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        this.publishedAt = publishedAt;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getPid() {
        return pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Timestamp getPublishedAt() {
        return publishedAt;
    }

    public String getTagsString() {
        return tagsString;
    }

    public void setTagsString(String tagsString) {
        this.tagsString = tagsString;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public void setPublishedAt(Timestamp publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt.setTime(publishedAt);
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = Timestamp.valueOf(publishedAt);
    }
    public boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean published) {
        isPublished = published;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt.setTime(createdAt);
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

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt.setTime(updatedAt);
    }

    @Override
    public String toString() {
        return "Post{" +
                "pid=" + pid +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", comments=" + comments +
                ", tags=" + tags +
                ", publishedAt=" + publishedAt +
                ", isPublished=" + isPublished +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
