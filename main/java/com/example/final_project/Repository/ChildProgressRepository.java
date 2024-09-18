package com.example.final_project.Repository;

import com.example.final_project.Model.ChildProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildProgressRepository extends JpaRepository<ChildProgress, Integer> {
    // [Mohammed]
    Optional<ChildProgress> findChildProgressById(Integer id);
    Optional<List<ChildProgress>> findAllByProgramId(Integer programId);
    Optional<List<ChildProgress>> findAllByChildId(Integer childId);
}
