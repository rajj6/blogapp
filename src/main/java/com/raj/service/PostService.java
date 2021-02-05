package com.raj.service;

import com.raj.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    List<Post> getAllPost();

    void savePost(Post post);

    void updatePost(Post post);

    void publishPost(Post post);

    Post getPostById(long id);

    void deletePostById(long id);

    void addTagsToPost(Post post);

    void generateTagsString(Post post);

    void removeAllTagsFromPost(Post post);

    Page<Post> findPaginated(int pageNo, int pageSize);
}
