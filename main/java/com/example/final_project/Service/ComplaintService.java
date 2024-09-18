package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Complaint;
import com.example.final_project.Model.User;
import com.example.final_project.Model.Parent;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.ComplaintRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ParentReposotiry;
import com.example.final_project.Model.Center;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ParentReposotiry parentRepository;
    private final CenterRepository centerRepository;
    private  final AuthRepository authRepository;
    //YARA
    public void addComplaint(Integer userId, Integer centerId, Complaint complaint) {
        // Fetch the center using the provided centerId
        Center center = centerRepository.findCenterById(centerId)
                .orElseThrow(() -> new ApiException("User not found"));

        // Throw an exception if the center doesn't exist
        if (center == null) {
            throw new ApiException("Center not found");
        }

        // Fetch the parent using the provided userId
        Parent parent = parentRepository.findParentById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        // Set the center and parent in the complaint entity
        complaint.setCenter(center);  // Set center reference
        complaint.setParent(parent);  // Set parent reference

        // Save the complaint
        complaintRepository.save(complaint);
    }
    //YARA
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }
    //YARA
    public Complaint getComplaintById(Integer id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
    }

//    public Complaint updateComplaint(Integer id, @Valid Complaint updatedComplaint) {
//        Complaint existingComplaint = getComplaintById(id);
//
//        // Update fields
//        existingComplaint.setContent(updatedComplaint.getContent());
//        existingComplaint.setCenter(updatedComplaint.getCenter());
//        existingComplaint.setParent(updatedComplaint.getParent());
//
//        return complaintRepository.save(existingComplaint);
//    }
//YARA
    public void deleteComplaint(Integer id) {
        Complaint existingComplaint = getComplaintById(id);
        complaintRepository.delete(existingComplaint);
    }
    //YARA
    public List<Complaint> getComplaintsByCenterId(Integer centerId) {
        Center center = centerRepository.findCenterById(centerId).orElseThrow(() -> new ApiException("Center not found"));
        return complaintRepository.findByCenterId(centerId);
    }

    public void replyToComplaint(Integer complaintId, String reply, Integer userId) {
        User user = authRepository.findUserById(userId).orElseThrow(() -> new ApiException("User not found"));
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ApiException("Complaint not found"));

        // Set the reply
        complaint.setReply(reply);

        // Save the updated complaint
        complaintRepository.save(complaint);
    }
    //YARA
    public List<Complaint> getComplaintsByUserId(Integer parentId) {
        User user = authRepository.findUserById(parentId).orElseThrow(() -> new ApiException("User not found"));

        return complaintRepository.findByParentId(parentId);
    }
}