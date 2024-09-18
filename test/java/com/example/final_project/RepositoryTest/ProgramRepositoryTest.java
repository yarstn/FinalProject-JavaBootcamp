package com.example.final_project.RepositoryTest;

import com.example.final_project.Model.Center;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ProgramRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProgramRepositoryTest {
    @Autowired
    ProgramRepository programRepository;

    @Autowired
    CenterRepository CenterRepository;

    @Autowired
    AuthRepository authRepository;

    User user, user2;

    Center center , center2;

    Program program1, program2, program3;

    List<Program> programList;


    Program program;

    @Autowired
    private CenterRepository centerRepository;

    @BeforeEach
    void setUp() {
        user=new User(null,"horseHouse","horseHouse@gmail.com",
                "12345Asf","0564735245","horse house","CENTER"
                ,false,null,null,null
                ,null,center,null);


        user2=new User(null,"EnglishAcademy","EnglishAcademy@gmail.com",
                "Asdf12345","0554666691","English Academy","CENTER"
                ,false,null,null,null
                ,null,center,null);


        center=new Center(null,"Riyadh",
                "horse Activity center able to learn your child about the horses world",
                "10027625", Center.Status.APPROVED,"Sport",0
                ,0,0.0,user,null,null,null,null);

        center2=new Center(null,"jeddah","English academy center provide newest technologies to improve child learn","10023974", Center.Status.APPROVED,"Academic",0
                ,0,0.0,user2,null,null
                ,null,null);


        program1 = new Program(1, "horse riding sessions",
                "in this program kids will learn techniques how to ride hourses",
                200,15,13,3,9,"Riyadh, tuwaiq ",
                "open",5,23465,5,
                LocalDate.parse("2024-11-15"),LocalDate.parse("2024-11-20"),
                center,null,null);

        program2 = new Program(null, " Swimming sessions for kids",
                "in this program kids will learn techniques how to ride swim",
                150,18,11,5,9,"Riyadh, Almohammadya ",
                "open",3,54632,10,
                LocalDate.parse("2025-02-10"),LocalDate.parse("2025-03-05"),
                center,null,null);

        program3 = new Program(3, "English sessions",
                "in this program kids will learn English riding, writing, and speaking",
                100,13,10,5,8,"Jeddah, Almurjan ",
                "close",2,12000,20,
                LocalDate.parse("2025-01-10"),LocalDate.parse("2025-02-04"),
                center2,null,null);

    }


    @Test
    public void findProgramByCenter(){
        authRepository.save(user);
        authRepository.save(user2);
        centerRepository.save(center);
        centerRepository.save(center2);
        programRepository.save(program1);
        programRepository.save(program2);
        programRepository.save(program3);
        programList =programRepository.findProgramByCenter(center2);
        Assertions.assertThat(programList.get(0).getCenter().getId()).isEqualTo(center2.getId());
    }


    @Test
    public void findProgramById(){
        authRepository.save(user);
        centerRepository.save(center);
        programRepository.save(program2);
        program =programRepository.findProgramById(program2.getId()).orElse(null);
        Assertions.assertThat(program).isEqualTo(program2);
    }

    @Test
    public void findByTitleContainingIgnoreCase(){
        authRepository.save(user2);
        centerRepository.save(center2);
        programRepository.save(program3);
        programList =programRepository.findByTitleContainingIgnoreCase("English sessions");
        Assertions.assertThat(programList.get(0).getTitle()).isEqualTo("English sessions");
    }

    @Test
    public void findProgramByStatusOpen(){
        authRepository.save(user);
        authRepository.save(user2);
        centerRepository.save(center);
        centerRepository.save(center2);
        programRepository.save(program1);
        programRepository.save(program2);
        programRepository.save(program3);
        programList = programRepository.findProgramByStatusOpen("open");
        Assertions.assertThat(programList.get(0).getStatus()).isEqualTo("open");
    }

//    @Test
//    public void findProgramByAgeRange(){
//        authRepository.save(user);
//        authRepository.save(user2);
//        centerRepository.save(center);
//        centerRepository.save(center2);
//        programRepository.save(program1);
//        programRepository.save(program2);
//        programRepository.save(program3);
//        programList=programRepository.findProgramByAgeRange(8,10).orElse(null);
//        Assertions.assertThat(programList.get(0).getMinAge(),programList.get(0).getMaxAge())
//                .isEqualsTo(programList);
//    }


}
