package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Comment;
import com.example.final_project.Model.Parent;
import com.example.final_project.Repository.CommentRepository;

import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ParentReposotiry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ParentReposotiry parentRepository;
    private final CenterRepository centerRepository;
//YARA
    public void addComment(Integer authId, Integer centerId, Comment comment) {
        Parent parent = parentRepository.findParentById(authId).orElseThrow(() -> new ApiException("Parent not found"));
        Center center = centerRepository.findCenterById(centerId).orElseThrow(() -> new ApiException("Center not found"));

        if(!parent.getId().equals(authId)){
            throw new ApiException("You don't have permission to add comment");
        }

        comment.setParent(parent);
        comment.setCenter(center);
        commentRepository.save(comment);
    }
//YARA
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
//YARA
    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
    //YARA
    public void updateComment(Integer id, @Valid Comment updatedComment) {
        Comment existingComment = getCommentById(id);

        // Update fields
        existingComment.setContent(updatedComment.getContent());
        existingComment.setCenter(updatedComment.getCenter());
        existingComment.setParent(updatedComment.getParent());
        existingComment.setCreatedAt(updatedComment.getCreatedAt());

        commentRepository.save(existingComment);
    }

    //YARA
    public void deleteComment(Integer id) {
        Comment existingComment = getCommentById(id);
        commentRepository.delete(existingComment);
    }
}