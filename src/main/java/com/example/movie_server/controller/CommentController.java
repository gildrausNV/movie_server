package com.example.movie_server.controller;

import com.example.movie_server.model.Comment;
import com.example.movie_server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/movie/{movieId}")
    public List<Comment> getMovieComments(@PathVariable String movieId){
        return commentService.getMovieComments(movieId);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> getUserComments(@PathVariable String userId){
        return commentService.getUserComments(userId);
    }

    @PostMapping("/{movieId}")
    public Comment saveComment(@PathVariable String movieId, @RequestBody Comment comment) {
        return commentService.save(movieId, comment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
    }

}
