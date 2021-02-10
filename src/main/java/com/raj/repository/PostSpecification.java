package com.raj.repository;

import com.raj.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostSpecification {

    public static Specification<Post> search(String keyword) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (keyword == null) {
                return null;
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
                    criteriaBuilder.like(root.get("content"), "%" + keyword + "%"),
                    criteriaBuilder.like(root.get("excerpt"), "%" + keyword + "%"),
                    criteriaBuilder.equal(root.join("author").get("name"), keyword),
                    criteriaBuilder.equal(root.join("tags").get("name"), keyword.trim().toUpperCase())
            );
        });
    }

    public static Specification<Post> filterPostByAuthorIdList(List<Long> uids) {
        if (uids == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (long uid : uids) {
                predicates.add(criteriaBuilder.equal(root.join("author").get("uid"), uid));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<Post> filterPostByTagIdList(List<Long> tids) {
        if (tids == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(long tid : tids){
                predicates.add(criteriaBuilder.equal(root.join("tags").get("tid"), tid));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<Post> filterPostAfter(Date startDate) {
        if (startDate == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("publishedAt"), startDate);
        });
    }

    public static Specification<Post> filterPostBefore(Date endDate) {
        if(endDate == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("publishedAt"), endDate);
        });
    }

    public static Pageable getPageable(int pageNo, int pageSize, String sortField, String order) {

        return PageRequest.of(pageNo - 1, pageSize, order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending());
    }
}
/*

    public static Specification<Post> searchPostByAuthorName(String authorName) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("author").get("name"), authorName);
        });
    }

    public static Specification<Post> searchPostByTagName(String tagName) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("tags").get("name"), tagName);
        });
    }

    public static Specification<Post> filterPostByTagId(Long tid) {
        if (tid == null) {
            return null;
        }

        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("tags").get("tid"), tid);
        });
    }

    public static Specification<Post> filterPostByAuthorId(Long uid) {
        if (uid == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("author").get("uid"), uid);
        });
    }

*/