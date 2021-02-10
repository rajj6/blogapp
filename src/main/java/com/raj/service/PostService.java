package com.raj.service;

import com.raj.model.Comment;
import com.raj.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
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

    Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String order);

    Page<Post> findAllPostWithFilters(int pageNo, int pageSize, String sortField, String order,
                                      String keyword, List<Long> tids, List<Long> uids,
                                      Date starDate, Date endDate);

}
