package com.example.final_project.Repository;


import com.example.final_project.Model.Center;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {

    Optional<Program> findProgramById(Integer id);

    List<Program> findProgramByCenter(Center center);
    List<Program> findByTitleContainingIgnoreCase(String title);

    @Query("select p from Program p where p.status=?1")
    List<Program> findProgramByStatusOpen(String open);


    @Query("SELECT p FROM Program p WHERE p.minAge >= :ageFrom AND p.maxAge <= :ageTo")
    Optional<List<Program>> findProgramByAgeRange(@Param("ageFrom") Integer ageFrom, @Param("ageTo") Integer ageTo);

    @Query("SELECT p FROM Program p where p.startDate BETWEEN :start AND :end")
    Optional<List<Program>> findAllProgramsByDateBetween(LocalDate start, LocalDate end);

    Optional<List<Child>> findAllByChild(Program program);
}
