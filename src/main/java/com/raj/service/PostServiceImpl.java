package com.raj.service;

import com.raj.model.Comment;
import com.raj.model.Post;
import com.raj.model.Tag;
import com.raj.repository.PostRepository;
import com.raj.repository.PostSpecification;
import com.raj.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

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
        post.setIsPublished(true);
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

    @Override
    public void addTagsToPost(Post post) {
        Collection<Tag> tagsList = post.getTags();
        String tagsString = "";
        for(String tag : post.getTagsString().split(" ")) {
            if(tagRepository.findTagByName(tag.toUpperCase().trim()) == null) {
                tagService.saveTag(new Tag(tag.toUpperCase().trim()));
            }
            tagsList.add(tagRepository.findTagByName(tag.toUpperCase().trim()));
            tagsString += tag.toUpperCase().trim()+" ";
        }
        post.setTagsString(tagsString);
    }

//    @Override
//    public void addCommentToPost(Post post, Comment comment) {
//        comment.setCreatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
//        comment.setUpdatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
//        post.getComments().add(comment);
//    }

    @Override
    public void generateTagsString(Post post) {
        String tagsString = "";
        for (Tag tag : post.getTags()) {
            tagsString += tag.getName() + " ";
        }
        post.setTagsString(tagsString);
    }

    @Override
    public void removeAllTagsFromPost(Post post) {
        post.getTags().clear();
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String order) {
        // Sort in ascending or descending order Published DataTime
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findAllPostWithFilters(int pageNo, int pageSize, String sortField, String order,
                                             String keyword, List<Long> tids, List<Long> uids,
                                             Date starDate, Date endDate) {
        return postRepository.findAll(PostSpecification.search(keyword)
                        .and(PostSpecification.filterPostByTagIdList(tids))
                        .and(PostSpecification.filterPostByAuthorIdList(uids))
                        .and(PostSpecification.filterPostAfter(starDate))
                        .and(PostSpecification.filterPostBefore(endDate)),
                PostSpecification.getPageable(pageNo, pageSize, sortField, order));
    }

    public Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String order, String keyword) {
        // Sort in ascending or descending order Published DataTime
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return postRepository.findAll(pageable);
    }

}
