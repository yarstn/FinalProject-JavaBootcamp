package com.example.final_project.ControllerTest;

import com.example.final_project.Controller.CenterController;
import com.example.final_project.Controller.ComplaintController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.final_project.DTO.CenterDTO;
import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.*;
import com.example.final_project.Service.CenterService;
import com.example.final_project.Service.ComplaintService;
import com.example.final_project.Service.ProgramService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CenterController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class CenterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CenterService centerService;

    @MockBean
    ProgramService programService;

    User user, user1, user2;

    Center center1, center2, center3;

    Program program1;
    Parent parent;
    ParentDTO parentDTO;
    Notification notification;

    CenterDTO centerDTO;
    List<Center> centers;

    @BeforeEach
    void setUp() {
        user = new User(3, "horseFirst", "FirstHorse@gmail.com",
                "Asdf12345", "0552636692", "First horse", "CENTER"
                , false, null, null, null
                , null, center1, null);

        user1 = new User(4, "EnglishAcademy", "EnglishAcademy@gmail.com",
                "Asdf12345", "0554666691", "English Academy", "CENTER"
                , false, null, null, null
                , null, center2, null);

        user2 = new User(5, "FootballAcademy", "FootballAcademy@gmail.com",
                "Asdf12345", "0554783691", "Football Academy", "CENTER"
                , false, null, null, null
                , parent, null, null);

        center1 = new Center(3, "jeddah", "horse Activity center able to learn your child about the horses world"
                , "29384756", Center.Status.IN_PROGRESS, "Sport", 0
                , 0, 0.0, user, null, null, null, null);

        center2 = new Center(4, "Riyadh", "English academy center provide newest technologies to improve child learn",
                "37468263", Center.Status.APPROVED, "Cultural", 7000
                , 15, 4.3, user1, null, null, null, null);

        centerDTO = new CenterDTO(3, "EnglishAcademy", "EnglishAcademy@gmail.com", "Asdf12345",
                "English Academy", "CENTER", "0554666991", "jeddah",
                "English academy center provide newest technologies to improve child learn",
                "37468263", null, "Academy");

        program1 = new Program(1, "horse riding sessions",
                "in this program kids will learn techniques how to ride hourses",
                200,15,13,3,9,"Riyadh, tuwaiq ",
                "open",5,23465,5,
                LocalDate.parse("2024-11-15"),LocalDate.parse("2024-11-20"),
                center1,null,null);

        parent = new Parent();
        parent.setId(1);
        parent.setUser(user2);

        parentDTO = new ParentDTO();
        // parentDTO.setId(1);
        parentDTO.setUsername("Kshalid");
        parentDTO.setPassword("49dc404b7733");
        parentDTO.setEmail("testuser@example.com");
        parentDTO.setName("Test User");
        parentDTO.setRole("PARENT");
        parentDTO.setPhoneNumber("1234567890");

        user.setUsername(parentDTO.getUsername());
        user.setPassword(parentDTO.getPassword());
        user.setEmail(parentDTO.getEmail());
        user.setName(parentDTO.getName());
        user.setRole(parentDTO.getRole());
        user.setPhoneNumber(parentDTO.getPhoneNumber());
        user2.setParent(parent);

    }

    @Test
    public void displayCenterFinancialReturnTest()throws Exception{
        Mockito.when(centerService.displayCenterFinancialReturns(center1.getId())).thenReturn(String.valueOf(center1));
        mockMvc.perform(get("/api/v1/center/display-total-center-financial-returns/{id}",center1.getId()))
                .andExpect(status().isOk());
    }


    @Test
    public void showMyCenterAccountTest()throws Exception{
        Mockito.when(centerService.showMyCenterAccount(center1.getId())).thenReturn(String.valueOf(center1));
        mockMvc.perform(get("/api/v1/center/Center-Account/{id}",center1.getId()))
                .andExpect(status().isOk());
    }

//    @Test
//    public void expandProgramTest(){
//        Mockito.when(programService.expandProgram(center1.getId(),1,LocalDate.parse("2024-12-20"))).thenReturn("expand ")
//    }

    @Test
    public void displayTotalNumberOfCenterProgramTest()throws Exception{
        Mockito.when(centerService.getTotalNumberOfCenterPrograms(center1.getId())).thenReturn(String.valueOf(center1));
        mockMvc.perform(get("/api/v1/center/display-total-number-center-program/{id}",center1.getId()))
                .andExpect(status().isOk());
    }





}
