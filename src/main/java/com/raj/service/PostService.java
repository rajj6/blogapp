package com.raj.service;

import com.raj.model.Post;

import java.util.List;

public interface PostService <Post, Long> {
    List<com.raj.model.Post> getAllPost();

    void savePost(com.raj.model.Post post);

    void updatePost(com.raj.model.Post post);

    void publishPost(com.raj.model.Post post);

    com.raj.model.Post getPostById(long id);

    void deletePostById(long id);
}
