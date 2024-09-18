package com.example.final_project.ControllerTest;

import com.example.final_project.Controller.ComplaintController;
import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Complaint;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ComplaintService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ComplaintController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ComplaintControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ComplaintService complaintService;


    Parent parent;
    User user,user2;
    ParentDTO parentDTO;

    Complaint complaint1,complaint2;

    Center center;

    List<Complaint> complaints;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);

        user2=new User(2,"horseHouse","horseHouse@gmail.com",
                "12345Asf","0564735245","horse house","CENTER"
                ,false,null,null,null
                ,null,center,null);

        center = new Center(2,"jeddah","horse Activity center able to learn your child about the horses world"
                ,"29384756", Center.Status.IN_PROGRESS,"Sport",0
                ,0,0.0,user2,null,null,null,null);

        parent = new Parent();
        parent.setId(1);
        parent.setUser(user);

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
        user.setParent(parent);


        complaint1 = new Complaint(1,parent,center,"i want make complaint about your program",null);

        complaints = new ArrayList();
        complaints.add(complaint1);
    }


    /*need to pass 'user id' in the path rather than from the @AuthenticationPrincipal User user
       to success the test*/
    @Test
    public void addComplaintTest() throws Exception {

        mockMvc.perform(post("/api/v1/complaint/add/{centerId}/{id}",center.getId(),parent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(complaint1)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllComplaintsTest() throws Exception {
        Mockito.when(complaintService.getAllComplaints()).thenReturn(complaints);
        mockMvc.perform(get("/api/v1/complaint/get-all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void getComplaintByIdTest() throws Exception{
        Mockito.when(complaintService.getComplaintById(complaint1.getId())).thenReturn(complaint1);
        mockMvc.perform(get("/api/v1/complaint/{id}",complaint1.getId()))
                .andExpect(status().isOk());
    }

    /*need to pass 'user id' in the path rather than from the @AuthenticationPrincipal User user
       to success the test*/
    @Test
    public void getComplaintsByCenterIdTest() throws Exception{
        Mockito.when(complaintService.getComplaintsByCenterId(center.getId())).thenReturn(complaints);
        mockMvc.perform(get("/api/v1/complaint/center/{id}",center.getId()))
                .andExpect(status().isOk());
    }

}
