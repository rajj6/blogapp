package com.raj.service;

import com.raj.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPost();

    void savePost(Post post);

    void updatePost(com.raj.model.Post post);

    void publishPost(com.raj.model.Post post);

    com.raj.model.Post getPostById(long id);

    void deletePostById(long id);

    void addTagsToPost(com.raj.model.Post post);

    void generateTagsString(com.raj.model.Post post);

    void removeAllTagsFromPost(Post post);
}
