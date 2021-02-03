package com.raj.service;

import com.raj.model.Post;
import com.raj.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public void savePost(Post post) {
        post.setCreatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        postRepository.save(post);
    }

    @Override
    public void updatePost(Post post) {
        if(post.getCreatedAt() == null) {
            post.setCreatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        }
        System.out.println("Is side PostServiceImpl update Post");
        post.setUpdatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        postRepository.save(post);
    }

    @Override
    public void publishPost(Post post) {
        if(post.getCreatedAt() == null) {
            post.setCreatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        }
        if(post.getUpdatedAt() == null) {
            post.setUpdatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        }
        post.setPublishedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        post.setPublished(true);
        postRepository.save(post);
    }

    @Override
    public Post getPostById(long id) {
        return postRepository.getOne(id);
    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }
}
