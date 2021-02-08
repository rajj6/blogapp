package com.raj.service;

import com.raj.model.Comment;
import com.raj.model.Post;
import com.raj.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getCommentById(long id) {
        return commentRepository.getOne(id);
    }

    public void addCommentToPost(Post post, Comment comment) {
        comment.setCreatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        comment.setPostId(post);
    }

    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }

    public void updateComment(Comment comment) {
        comment.setUpdatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        commentRepository.save(comment);
    }
}
