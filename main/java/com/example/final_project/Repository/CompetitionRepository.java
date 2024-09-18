package com.example.final_project.Repository;

import com.example.final_project.Model.Child;
import com.example.final_project.Model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    // [Mohammed]
    Optional<Competition> findCompetitionById(Integer id);
    Optional<Competition> findCompetitionByName(String name);
    @Query("SELECT c FROM Competition c where c.startDate BETWEEN :start AND :end")
    Optional<List<Competition>> findAllByDateBetween(LocalDate start, LocalDate end);
    Optional<List<Competition>> findAllByType(String type);
    @Query("SELECT c FROM Competition c WHERE c.minAge >= :ageFrom AND c.maxAge <= :ageTo")
    Optional<List<Competition>> findCompetitionsByAgeRange(@Param("ageFrom") Integer ageFrom, @Param("ageTo") Integer ageTo);
    Optional<List<Competition>> findAllByParticipants(Child child);
}
