package com.raj.repository;

import com.raj.model.Post;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PostSpecification {

    public static Specification<Post> titleContains(String keyword) {
        return ((root, criteriaQuery, criteriaBuilder) ->  {
            return criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
        });
    }
}
