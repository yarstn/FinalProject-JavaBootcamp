package com.example.final_project.RepositoryTest;

import com.example.final_project.Model.Competition;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CompetitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompetitionRepositoryTest {

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    AuthRepository authRepository;
    User user;

    Competition competition1;
    Competition competition2;
    Competition competition;

    List<Competition> competitions;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setEmail("emdil@email.com");
        user.setRole("ADMIN");
        user.setName("testName");
        user.setPhoneNumber("0500000000");
        authRepository.save(user);

        competition1 = new Competition();
        competition1.setName("Competition 1");
        competition1.setParticipant(10);
        competition1.setMinAge(10);
        competition1.setMaxAge(16);
        competition1.setStartDate(LocalDate.of(2024, 10, 1));
        competition1.setEndDate(LocalDate.of(2024, 10, 2));
        competition1.setType("Type-1");
        competitionRepository.save(competition1);

        competition2 = new Competition();
        competition2.setName("Competition 2");
        competition2.setParticipant(30);
        competition2.setMinAge(7);
        competition2.setMaxAge(16);
        competition2.setStartDate(LocalDate.of(2024, 11, 1));
        competition2.setEndDate(LocalDate.of(2024, 11, 2));
        competition1.setType("Type-2");
        competitionRepository.save(competition2);
    }

    @Test
    public void testFindCompetitionById() {
        Optional<Competition> competition = competitionRepository.findById(competition1.getId());
        assertEquals("Competition7", competition.get().getName());
    }


    @Test
    public void findCompetitionByName(){
        competitionRepository.save(competition1);
        competition = competitionRepository.findCompetitionByName(competition1.getName()).orElse(null);
        Assertions.assertThat(competition).isEqualTo(competition1);
    }

    @Test
    public void findCompetitionByType(){
        competitionRepository.save(competition1);
        competitions = competitionRepository.findAllByType("Sport").orElse(null);
        Assertions.assertThat(competitions.get(0).getType()).isEqualTo("Sport");
    }

//    @Test
//    public void findAllByParticipants(){
//        competitionRepository.save(competition1);
//        competitionRepository.save(competition2);
//        childRepository.save(child1);
//
//        competitions = competitionRepository.findAllByParticipants(child1).orElse(null);
//        Assertions.assertThat(competitions.get(0).getParticipants()).isEqualTo(child1);
//    }

}
