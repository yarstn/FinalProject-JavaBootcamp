package com.example.final_project.ServiceTest;

import com.example.final_project.Model.Center;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ProgramRepository;
import com.example.final_project.Service.ProgramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProgramServiceTest {

    @InjectMocks
    private ProgramService programService;

    @Mock
    ProgramRepository programRepository;

    @Mock
    CenterRepository centerRepository;

    @Mock
    AuthRepository authRepository;

    User user, user2;

    Center center , center2;

    Program program1, program2, program3;

    List<Program> programList;


    Program program;

    @BeforeEach
    void setUp() {
        user=new User(1,"horseHouse","horseHouse@gmail.com",
                "12345Asf","0564735245","horse house","CENTER"
                ,false,null,null,null
                ,null,center,null);


        user2=new User(null,"EnglishAcademy","EnglishAcademy@gmail.com",
                "Asdf12345","0554666691","English Academy","CENTER"
                ,false,null,null,null
                ,null,center,null);


        center=new Center(1,"Riyadh",
                "horse Activity center able to learn your child about the horses world",
                "10027625", Center.Status.APPROVED,"Sport",0
                ,0,0.0,user,null,null, null,null);

        center2=new Center(null,"jeddah","English academy center provide newest technologies to improve child learn","10023974", Center.Status.APPROVED,"Academic",0
                ,0,0.0,user2,null,null
                ,null,null);


        program1 = new Program(1, "horse riding sessions",
                "in this program kids will learn techniques how to ride hourses",
                200,15,13,3,9,"Riyadh, tuwaiq ",
                "close",5,23465,5,
                LocalDate.parse("2024-11-15"),LocalDate.parse("2024-11-20"),
                center,null,null);


        program2 = new Program(2, " Swimming sessions for kids",
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
    public void addProgramTest(){


        when(centerRepository.findCenterById(center.getId())).thenReturn(Optional.of(center));
        when(programRepository.findProgramById(program1.getId())).thenReturn(Optional.of(program1));
        programService.addProgram(center.getId(),program1);

        programRepository.save(program1);
        centerRepository.save(center);


    }

    @Test
    public void deleteProgramTest(){
        user.setCenter(center);
        when(authRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        when(centerRepository.findCenterById(center.getId())).thenReturn(Optional.of(center));
        when(programRepository.findProgramById(program1.getId())).thenReturn(Optional.of(program1));

        programService.deleteProgram(center.getId(),program1.getId());
        programRepository.save(program1);
        centerRepository.save(center);
    }

    @Test
    public void updateProgramTest(){

        user.setCenter(center);
        authRepository.save(user);

        when(programRepository.findProgramById(program1.getId())).thenReturn(Optional.of(program1));
        when(centerRepository.findCenterById(center.getId())).thenReturn(Optional.of(center));
        when(authRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        programService.updateProgram(center.getId(),program1.getId(),program1);

        programRepository.save(program1);
        centerRepository.save(center);
    }

    @Test
    public void getProgramTest(){
        user.setCenter(center);
        authRepository.save(user);

        when(programRepository.findProgramById(program1.getId())).thenReturn(Optional.of(program1));
        when(centerRepository.findCenterById(center.getId())).thenReturn(Optional.of(center));
        when(authRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        programService.setProgramStatus(user.getId(),program1.getId(),"open");

        programRepository.save(program1);
    }

    @Test
    public void expandProgramTest(){

        user.setCenter(center);
        authRepository.save(user);

        when(programRepository.findProgramById(program1.getId())).thenReturn(Optional.of(program1));
        when(centerRepository.findCenterById(center.getId())).thenReturn(Optional.of(center));
        when(authRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        programService.expandProgram(user.getId(),program1.getId(),LocalDate.parse("2024-12-20"));

        programRepository.save(program1);


    }

    @Test
    public void deleteAllClosedProgramsTest(){

        user.setCenter(center);
        authRepository.save(user);
        programRepository.save(program1);
        programRepository.save(program2);

        when(programRepository.findProgramById(program1.getId())).thenReturn(Optional.of(program1));
        when(centerRepository.findCenterById(center.getId())).thenReturn(Optional.of(center));
        when(authRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        programService.deleteAllClosedPrograms(center.getId());

        centerRepository.save(center);
    }

    @Test
    public void displayChildrenNumbersTest(){
        user.setCenter(center);
        authRepository.save(user);
        programRepository.save(program1);


        when(programRepository.findProgramById(program1.getId())).thenReturn(Optional.of(program1));
        when(centerRepository.findCenterById(center.getId())).thenReturn(Optional.of(center));
        when(authRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        programService.displayChildrenNumbers(center.getId(),program1.getId());
    }

}
