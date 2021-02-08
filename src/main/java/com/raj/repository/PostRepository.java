package com.raj.repository;

import com.raj.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    @Query("SELECT p FROM Post p WHERE CONCAT(p.title, p.excerpt, p.content) LIKE %?1%")
    List<Post> search(String keyword, Pageable pageable);

}
