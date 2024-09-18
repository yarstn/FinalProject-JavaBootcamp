package com.example.final_project.Repository;

import com.example.final_project.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
//    Optional<Comment> findCommentById(Integer id);
}
