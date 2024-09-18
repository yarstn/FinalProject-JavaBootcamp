package com.example.final_project.Repository;
import com.example.final_project.Model.Parent;

import com.example.final_project.Model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
    Optional<Child> findChildById(Integer integer);
    List<Child> findAllByParent(Parent parent);

}
