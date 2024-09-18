package com.example.final_project.ServiceTest;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Complaint;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ComplaintRepository;
import com.example.final_project.Repository.ParentReposotiry;
import com.example.final_project.Service.ComplaintService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ComplaintServiceTest {

    @InjectMocks
    private ComplaintService complaintService;

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private ParentReposotiry parentRepository;

    @Mock
    private CenterRepository centerRepository;

    @Mock
    private AuthRepository authRepository;

    private Complaint complaint;
    private Parent parent;
    private Center center;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test objects
        parent = new Parent();
        parent.setId(1);

        center = new Center();
        center.setId(1);

        complaint = new Complaint();
        complaint.setId(1);
        complaint.setContent("Test complaint");
        complaint.setParent(parent);
        complaint.setCenter(center);
    }

    // Test for adding a complaint
    @Test
    void addComplaint_shouldSaveComplaint() {
        //  Center and Parent exist
        when(centerRepository.findCenterById(anyInt())).thenReturn(Optional.of(center));
        when(parentRepository.findParentById(anyInt())).thenReturn(Optional.of(parent));

        //adding a complaint
        complaintService.addComplaint(1, 1, complaint);

        // complaint is saved
        verify(complaintRepository, times(1)).save(complaint);
    }

    @Test
    void addComplaint_shouldThrowExceptionIfCenterNotFound() {
        // Center does not exist
        when(centerRepository.findCenterById(anyInt())).thenReturn(Optional.empty());

        //Exception
        assertThrows(ApiException.class, () -> complaintService.addComplaint(1, 1, complaint));
    }

    @Test
    void addComplaint_shouldThrowExceptionIfParentNotFound() {
        // Center exists parent does not
        when(centerRepository.findCenterById(anyInt())).thenReturn(Optional.of(center));
        when(parentRepository.findParentById(anyInt())).thenReturn(Optional.empty());

        // When/Then: Exception should be thrown
        assertThrows(ApiException.class, () -> complaintService.addComplaint(1, 1, complaint));
    }

    // Test for getting all complaints
    @Test
    void getAllComplaints_shouldReturnComplaints() {
        when(complaintRepository.findAll()).thenReturn(List.of(complaint));

        List<Complaint> result = complaintService.getAllComplaints();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // Test for getting complaint by ID
    @Test
    void getComplaintById_shouldReturnComplaint() {
        when(complaintRepository.findById(anyInt())).thenReturn(Optional.of(complaint));

        Complaint result = complaintService.getComplaintById(1);

        assertNotNull(result);
        assertEquals(complaint.getId(), result.getId());
    }

    @Test
    void getComplaintById_shouldThrowExceptionIfNotFound() {
        // Complaint does not exist
        when(complaintRepository.findById(anyInt())).thenReturn(Optional.empty());

        //  Exception
        assertThrows(RuntimeException.class, () -> complaintService.getComplaintById(1));
    }

    // Test for deleting a complaint
    @Test
    void deleteComplaint_shouldDeleteComplaint() {
        //Complaint exists
        when(complaintRepository.findById(anyInt())).thenReturn(Optional.of(complaint));

        //  deleting a complaint
        complaintService.deleteComplaint(1);

        // complaint is deleted
        verify(complaintRepository, times(1)).delete(complaint);
    }

    // Test for replying to a complaint
    @Test
    void replyToComplaint_shouldUpdateReply() {
        // Complaint exists, and User exists
        User user = new User();
        user.setId(1);

        when(complaintRepository.findById(anyInt())).thenReturn(Optional.of(complaint));
        when(authRepository.findUserById(anyInt())).thenReturn(Optional.of(user));

        //replying to a complaint
        complaintService.replyToComplaint(1, "Reply message", 1);

        //  verify that the reply is set
        assertEquals("Reply message", complaint.getReply());
        verify(complaintRepository, times(1)).save(complaint);
    }

    @Test
    void replyToComplaint_shouldThrowExceptionIfComplaintNotFound() {
        //  Complaint does not exist
        when(complaintRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Exception
        assertThrows(ApiException.class, () -> complaintService.replyToComplaint(1, "Reply message", 1));
    }
}
