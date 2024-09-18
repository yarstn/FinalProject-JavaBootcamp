package com.example.final_project.ControllerTest;

import com.example.final_project.Controller.CommentController;
import com.example.final_project.Model.Comment;
import com.example.final_project.Service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;



    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setId(1);
        comment.setContent("This is a comment.");
    }


    //  getting all comments
    @Test
    @WithMockUser
    void getAllComments_shouldReturnListOfComments() throws Exception {
        List<Comment> commentList = Arrays.asList(
                new Comment(1, "First comment", null, null, null),
                new Comment(2, "Second comment", null, null, null)
        );

        Mockito.when(commentService.getAllComments()).thenReturn(commentList);

        mockMvc.perform(get("/api/v1/comments/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].content", is("First comment")))
                .andExpect(jsonPath("$[1].content", is("Second comment")));
    }

    //  comment by ID
    @Test
    @WithMockUser
    void getCommentById_shouldReturnComment() throws Exception {
        Mockito.when(commentService.getCommentById(anyInt())).thenReturn(comment);

        mockMvc.perform(get("/api/v1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("This is a comment.")));
    }


}
