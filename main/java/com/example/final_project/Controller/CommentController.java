package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.Comment;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
//YARA
    @PostMapping("/add/{centerId}")
    public ResponseEntity<ApiResponse> addComment(@AuthenticationPrincipal User user, @PathVariable Integer centerId, @Valid @RequestBody Comment comment) {
        commentService.addComment(user.getId(), centerId, comment);
        return ResponseEntity.ok(new ApiResponse("Added comment successfully"));
    }
//YARA
    @GetMapping("/get-all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
//YARA
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }
//YARA
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Comment> updateComment(@PathVariable Integer id, @Valid @RequestBody Comment updatedComment, @AuthenticationPrincipal User user) {
//        updatedComment.setParent(user.getParent()); // Set the parent if necessary
//        Comment comment = commentService.updateComment(id, updatedComment);
//        return ResponseEntity.ok(comment);
//    }
//YARA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(200).body("Comment deleted");
    }

}