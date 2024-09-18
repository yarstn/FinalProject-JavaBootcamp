package com.example.final_project.Repository;

import com.example.final_project.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//YARA
public interface ParentReposotiry  extends JpaRepository<Parent, Integer> {
    Optional<Parent> findParentById(Integer id);
    Optional<Parent> findParentByUser_Id(Integer userId);
}
