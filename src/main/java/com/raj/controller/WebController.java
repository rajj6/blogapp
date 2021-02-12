package com.raj.controller;

import com.raj.model.*;
import com.raj.service.CommentService;
import com.raj.service.PostService;
import com.raj.service.TagService;
import com.raj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
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
    CommentService commentService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/test")
    public String test(Principal principal,Model model) {
        System.out.println("\n*\n*\n*\nIn side Test ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(principal != null){
            System.out.println(principal.getName());
            System.out.println(principal.getClass());
            UserPrincipal loggedInUser = getLoggedInUser();
            System.out.println("Logged in user's Username "+loggedInUser.getUsername());
            System.out.println("Logged in user's Name "+loggedInUser.getName());
        }else {
            System.out.println("Principal is null");
        }
        model.addAttribute("post",postService.getPostById(6l));
//        ArrayList<GrantedAuthority> authorities = authentication.getAuthorities();
        for( GrantedAuthority i : authentication.getAuthorities()) {
            System.out.println(i.getAuthority());
        }
        return "test";
    }

    private UserPrincipal getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal)authentication.getPrincipal();
    }

    @GetMapping("/")
    public String main(Model model) {
        return showPosts(1,"publishedAt","dec", model, "", null, null, Date.valueOf("2021-02-01"), Date.valueOf("2021-03-01"));
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

        int pageSize = 5;

        if (sortField == null) {
            sortField = "publishedAt";
        }
        if (order == null) {
            order = "dec";
        }
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
        Page<Post> page = postService.findAllPostWithFilters(pageNo, pageSize, sortField, order, keyword, tagId, authorId, startDate, endDate);
        List<Post> posts= page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("order", order);
        model.addAttribute("sortField", sortField);
        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword);
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
        model.addAttribute("loggedInUser", getLoggedInUser());
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
        System.out.println("Going to save this post "+post);
        post.setAuthor(userService.getUserById(getLoggedInUser().getUid()));
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
    public String addComment(@PathVariable(value = "id") long id, Model model, Principal principal) {
        Comment comment = new Comment();
        if (principal != null) {
            model.addAttribute("loggedInUser", getLoggedInUser());
            comment.setName(getLoggedInUser().getName());
            comment.setEmail(getLoggedInUser().getUsername());
        }
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