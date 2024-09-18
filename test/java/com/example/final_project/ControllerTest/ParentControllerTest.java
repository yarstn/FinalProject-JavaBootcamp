package com.example.final_project.ControllerTest;

import com.example.final_project.Controller.ParentController;
import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CenterService;
import com.example.final_project.Service.ParentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParentController.class)
class ParentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CenterService centerService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParentService parentService;


    private Parent parent;
    private User user;
    private ParentDTO parentDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);

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
    }

    @Test
    @WithMockUser
    void getAllParents_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/parent/get-all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void addParent_shouldReturnCreated() throws Exception {
        // Mock the service method
        // doNothing().when(parentService).addParent(any(ParentDTO.class));
        // parentService.addParent(parentDTO);

        // Perform the POST request
        mockMvc.perform(post("/api/v1/parent/register")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(parentDTO)))
                .andExpect(status().isOk());
    }




}
