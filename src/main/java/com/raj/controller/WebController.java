package com.raj.controller;

import com.raj.model.Comment;
import com.raj.model.Post;
import com.raj.model.Tag;
import com.raj.repository.PostRepository;
import com.raj.repository.PostSpecification;
import com.raj.service.CommentService;
import com.raj.service.PostService;
import com.raj.service.TagService;
import com.raj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    PostService postService;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentService commentService;


    @GetMapping("/test")
    public String test() {
        Pageable pageable = PageRequest.of(0, 10);

        System.out.println("\n*\n*\n*\nIn side Test ");
        Date starDate = Date.valueOf("2021-02-01");
        Date endDate = Date.valueOf("2021-02-28");
        int pageNo = 1;
        int pageSize = 5;
        String keyword="The";
        String sortField = "publishedAt";
        String order = "dec";
        List<Long> uids = new ArrayList<>();
//        uids.add(2l);
        List<Long> tids = new ArrayList<>();
//        tids.add(83l);
//        tids.add(84l);
        System.out.println("Start date is " + starDate);
//        System.out.println(PostSpecification.filterPostAfter(starDate).toString());
        System.out.println(postService.findAllPostWithFilters(pageNo, pageSize, sortField, order, keyword, tids, uids, starDate, endDate));

//        System.out.println(postRepository.findAll(PostSpecification.searchPostByAuthorName("ruby")));
//        System.out.println(postRepository.findAll(PostSpecification.search("").and(PostSpecification.filterPostByAuthorId(2l))));
        return "test";
    }

    @GetMapping("/")
    public String main(Model model) {
        return showPosts(1,"publishedAt","dec", model, null, null, null, null, null);
    }
    @GetMapping("/home/{pageNo}")
    public String showPosts(@PathVariable(value = "pageNo") int pageNo,
                            @RequestParam(value = "sortField", required = false) String sortField,
                            @RequestParam(value = "order", required = false) String order,
                            Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "tagId", required = false) List<Long> tagId,
                            @RequestParam(value = "authorId", required = false) List<Long> authorId,
                            @RequestParam(value = "startDate", required = false) Date startDate,
                            @RequestParam(value = "endDate", required = false) Date endDate) {

        int pageSize = 3;

        if (sortField == null) {
            sortField = "publishedAt";
        }
        if (order == null) {
            order = "dec";
        }
        // Testing
        // Generating Tag Id String for URL
        String tagIdString = "";
        if(tagId != null){
            for( Long tid : tagId ) {
                tagIdString += ("&tagId=" + tid);
            }
        }


        // Generating Tag Id String for URL
        String authorIdString = "";
        if(authorId != null) {
            for( long uid : authorId ){
                authorIdString += ("&authorId=" + uid);
            }
        }

        System.out.println("Keyword is"+ keyword);
        System.out.println("tagId is/are "+tagId);
        System.out.println("authorId is/are "+authorId);
        System.out.println("startDate is " + startDate);
        System.out.println("endDate is " + endDate);

//        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, order);
        Page<Post> page = postService.findAllPostWithFilters(pageNo, pageSize, sortField, order, keyword, tagId, authorId, startDate, endDate);
        List<Post> posts= page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("order", order);
        model.addAttribute("sortField", sortField);
        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword);
//        model.addAttribute("tagId",tagId);
//        model.addAttribute("authorId", authorId);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("tagIdString", tagIdString);
        model.addAttribute("authorIdString", authorIdString);

        model.addAttribute("all_tags", tagService.getAllTags());
        model.addAttribute("all_authors", userService.getAllUsers());

        return "home";
    }

    @GetMapping("/showPost/{id}")
    public String showPost(@PathVariable(value = "id") long id, Model model) {
        Post post = postService.getPostById(id);
        postService.generateTagsString(post);
        model.addAttribute("post", post);
        return "show_post";
    }

    @GetMapping("/showNewBlogForm")
    public String showNewPostForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "new_post";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post) {
        postService.savePost(post);
        return "redirect:/home";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute("post") Post post) {
        postService.removeAllTagsFromPost(post);
        postService.addTagsToPost(post);
        postService.updatePost(post);
        return "redirect:/";
    }

    @PostMapping("/publishPost")
    public String publishPost(@ModelAttribute("post") Post post) {
        postService.addTagsToPost(post);
        postService.publishPost(post);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Post post = postService.getPostById(id);
        postService.generateTagsString(post);
        model.addAttribute("post", post);
        return "update_post";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value = "id") long id) {
        this.postService.deletePostById(id);
        return "redirect:/";
    }

    @GetMapping("/addComment/{id}")
    public String addComment(@PathVariable(value = "id") long id, Model model) {
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        model.addAttribute("postId", id);
        return "comment_form";
    }

    @PostMapping("/saveComment/{id}")
    public String saveComment(@PathVariable(value = "id") long id,
                              @ModelAttribute("comment") Comment comment,
                              Model model) {
        commentService.addCommentToPost(postService.getPostById(id), comment);
        commentService.saveComment(comment);
        return "redirect:/showPost/" + id;
    }

    @PostMapping("/updateComment")
    public String updateComment(@ModelAttribute("comment") Comment comment, Model model,
                                @ModelAttribute("postId") long postId) {
        commentService.addCommentToPost(postService.getPostById(postId),comment);
        commentService.updateComment(comment);
        return "redirect:/showPost/" + postId;
    }

    @GetMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable(value = "id") long id) {
        long postId = commentService.getCommentById(id).getPostId().getPid();
        commentService.deleteCommentById(id);
        return "redirect:/showPost/" + postId;
    }

    @GetMapping("/showCommentUpdateForm/{id}")
    public String deleteComment(@PathVariable(value = "id") long id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", commentService.getCommentById(id));
        model.addAttribute("postId", comment.getPostId().getPid());
        return "comment_update_form";
    }

    @GetMapping("/addTag")
    public String addTag(Model model) {
        model.addAttribute("tag",new Tag());
        return "new_tag";
    }

    @PostMapping("/saveTag")
    public String saveTag(@ModelAttribute("tag") Tag tag){
        return "";
    }
}