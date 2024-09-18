package com.example.final_project.ControllerTest;

import com.example.final_project.Controller.ProgramController;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ProgramService;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProgramController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProgramControllerTest {

    @MockBean
    ProgramService programService;

    @Autowired
    MockMvc mockMvc;

    User user, user2;

    Center center, center2;

    Program program1, program2, program3;

    List<Program> programList=new ArrayList<>();
    List<Program> programs=new ArrayList<>();



    Program program;


    @BeforeEach
    void setUp() {
        user = new User(1, "horseHouse", "horseHouse@gmail.com",
                "12345Asf", "0564735245", "horse house", "CENTER"
                , false, null, null, null
                , null, center, null);


        user2 = new User(2, "EnglishAcademy", "EnglishAcademy@gmail.com",
                "Asdf12345", "0554666691", "English Academy", "CENTER"
                , false, null, null, null
                , null, center, null);


        center = new Center(1, "Riyadh",
                "horse Activity center able to learn your child about the horses world",
                "10027625", Center.Status.APPROVED, "Sport", 0
                , 0, 0.0, user, null, null, null, null);

        center2 = new Center(2, "jeddah", "English academy center provide newest technologies to improve child learn", "10023974", Center.Status.APPROVED, "Academic", 0
                , 0, 0.0, user2, null, null
                , null, null);


        program1 = new Program(1, "horse riding sessions",
                "in this program kids will learn techniques how to ride hourses",
                200, 15, 13, 3, 9, "Riyadh, tuwaiq ",
                "open", 5, 23465, 5,
                LocalDate.parse("2024-11-15"), LocalDate.parse("2024-11-20"),
                center, null, null);

        program2 = new Program(2, " Swimming sessions for kids",
                "in this program kids will learn techniques how to ride swim",
                150, 18, 11, 5, 9, "Riyadh, Almohammadya ",
                "open", 3, 54632, 10,
                LocalDate.parse("2025-02-10"), LocalDate.parse("2025-03-05"),
                center, null, null);

        program3 = new Program(3, "English sessions",
                "in this program kids will learn English riding, writing, and speaking",
                100, 13, 10, 5, 8, "Jeddah, Almurjan ",
                "close", 2, 12000, 20,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-02-04"),
                center2, null, null);
        programs.add(program1);
        programs.add(program2);
        programs.add(program3);
    }


//    @Test
//    public void addProgramTest() throws Exception {
//
//        mockMvc.perform(post("/api/v1/program/add-program")
//                        .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(program1)))
//                .andExpect(status().isOk());
//    }


    @Test
    public void getProgramTest() throws Exception {
        Mockito.when(programService.getAllPrograms()).thenReturn(programs);
        mockMvc.perform(get("/api/v1/program/get-programs"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("horse riding sessions"));

    }

    /*need to pass 'user id' in the path rather than from the @AuthenticationPrincipal User user
    to success the test*/
    @Test
    public void deleteProgramTest() throws Exception {

        mockMvc.perform(delete("/api/v1/program/delete-program/{programid}/{id}",program1.getId(),user.getId()))
                .andExpect(status().isOk());
    }

    /*need to pass 'user id' in the path rather than from the @AuthenticationPrincipal User user
    to success the test*/
    @Test
    public void deleteAllClosedProgramTest() throws Exception {

        mockMvc.perform(delete("/api/v1/program/delete-all-closed-programs/{id}",user.getId()))
                .andExpect(status().isOk());
    }

    /*need to pass 'user id' in the path rather than from the @AuthenticationPrincipal User user
    to success the test*/
    @Test
    public void displayProgramsFinancialReturnsTest() throws Exception {
        mockMvc.perform(get("/api/v1/program/display-programs-financial-returns/{programid}/{id}",program1.getId(),user.getId()))
                .andExpect(status().isOk());
    }


    @Test
    public void getProgramByAgeTest() throws Exception {
        mockMvc.perform(get("/api/v1/program/get-program-by/min-age/{minage}/max-age/{maxage}",8,11))
                .andExpect(status().isOk());
    }

    /*need to pass 'user id' in the path rather than from the @AuthenticationPrincipal User user
    to success the test*/
    @Test
    public void displayNumOfChildrenInTheProgramTest() throws Exception {
        mockMvc.perform(get("/api/v1/program/display-number-of-children-in-the-program/{programid}/{id}"
                        , program1.getId(), user.getId()))
                .andExpect(status().isOk());
    }
}