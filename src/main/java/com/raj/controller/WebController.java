package com.raj.controller;

import com.raj.model.Comment;
import com.raj.model.Post;
import com.raj.model.Tag;
import com.raj.repository.PostRepository;
import com.raj.repository.TagRepository;
import com.raj.repository.UserRepository;
import com.raj.service.CommentService;
import com.raj.service.PostService;
import com.raj.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    PostService postService;

    @Autowired
    TagService tagService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentService commentService;


    @GetMapping("/test")
    public String test() {
        Pageable pageable = PageRequest.of(0, 10);
        System.out.println(postRepository.search("Raj", pageable));
        return "test";
    }

    @GetMapping("/")
    public String main(Model model) {
        return showPosts(1,"publishedAt","asc",model);
    }

    @GetMapping("/home/{pageNo}")
    public String showPosts(@PathVariable(value = "pageNo") int pageNo,
                            @RequestParam(value = "sortField", required = false) String sortField,
                            @RequestParam(value = "order", required = false) String order, Model model){
        int pageSize = 3;

        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, order);
        List<Post> posts= page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortDir", order);
        model.addAttribute("sortField", sortField);
        model.addAttribute("posts", posts);
        System.out.println("Here");
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
        return "redirect:/";
    }

    @GetMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable(value = "id") long id) {
        commentService.deleteCommentById(id);
        return "redirect:/";
    }

    @GetMapping("/showCommentUpdateForm/{id}")
    public String deleteComment(@PathVariable(value = "id") long id, Model model) {
        Comment comment = commentService.getCommentById(id);
        System.out.println(comment.getCid() + " " + comment.getCreatedAt() + " " +comment.getName() + " " + comment.getEmail() +  " " + comment.getCommentMsg());
        model.addAttribute("comment", commentService.getCommentById(id));
        model.addAttribute("postId", comment.getPostId().getPid());
        System.out.println("postId id " + comment.getPostId().getPid());
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