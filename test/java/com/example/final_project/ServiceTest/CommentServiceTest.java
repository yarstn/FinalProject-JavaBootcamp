package com.example.final_project.ServiceTest;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Comment;
import com.example.final_project.Model.Parent;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.CommentRepository;
import com.example.final_project.Repository.ParentReposotiry;
import com.example.final_project.Service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ParentReposotiry parentRepository;

    @Mock
    private CenterRepository centerRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private Parent parent;
    private Center center;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parent = new Parent();
        parent.setId(1);

        center = new Center();
        center.setId(1);

        comment = new Comment();
        comment.setId(1);
        comment.setContent("Test comment");
        comment.setParent(parent);
        comment.setCenter(center);
    }

    //  adding a comment
    @Test
    void addComment_shouldSaveCommentWhenValid() {
        when(parentRepository.findParentById(1)).thenReturn(Optional.of(parent));
        when(centerRepository.findCenterById(1)).thenReturn(Optional.of(center));

        commentService.addComment(1, 1, comment);

        verify(commentRepository, times(1)).save(comment);
    }

    // Test for adding a comment when the parent is not found
    @Test
    void addComment() {
        when(parentRepository.findParentById(1)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            commentService.addComment(1, 1, comment);
        });

        assertEquals("Parent not found", exception.getMessage());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    //  getting all comments++++++++



    @Test
    void getAllComments() {
        List<Comment> commentList = Arrays.asList(comment, new Comment());
        when(commentRepository.findAll()).thenReturn(commentList);

        List<Comment> result = commentService.getAllComments();

        assertEquals(2, result.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void getCommentById() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        Comment result = commentService.getCommentById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(commentRepository, times(1)).findById(1);
    }

    @Test
    void getCommentById_ThrowException() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentService.getCommentById(1);
        });

        assertEquals("Comment not found", exception.getMessage());
        verify(commentRepository, times(1)).findById(1);
    }

    @Test
    void deleteComment() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void deleteComment_ThrowException() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentService.deleteComment(1);
        });

        assertEquals("Comment not found", exception.getMessage());
        verify(commentRepository, never()).delete(any(Comment.class));
    }
}
