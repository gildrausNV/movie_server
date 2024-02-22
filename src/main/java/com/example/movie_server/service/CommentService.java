package com.example.movie_server.service;

import com.example.movie_server.model.Comment;
import com.example.movie_server.model.Movie;
import com.example.movie_server.model.User;
import com.example.movie_server.repository.CommentRepository;
import com.example.movie_server.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;

    public List<Comment> getMovieComments(String movieId) {
        return commentRepository.findCommentsByMovie_Id(movieId);
    }

    public List<Comment> getUserComments(String userId) {
        return commentRepository.findCommentsByUser_Id(userId);
    }

    public Comment save(String movieId, Comment comment) {
        User currentlyLoggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Movie movie = movieRepository.findById(movieId).orElseThrow(NoSuchElementException::new);
        comment.setUser(currentlyLoggedInUser);
        comment.setMovie(movie);
        return commentRepository.save(comment);
    }

    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }
}
