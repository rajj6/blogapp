package com.raj.repository;

import com.raj.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("FROM Tag t WHERE t.name = :tagName")
    Tag findTagByName(String tagName);
}
