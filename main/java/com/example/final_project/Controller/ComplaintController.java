package com.example.final_project.Controller;

import com.example.final_project.Model.Complaint;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaint")
public class ComplaintController {
@Autowired
    private ComplaintService complaintService;
//YARA
    @PostMapping("/add/{centerId}")
    public ResponseEntity<String> addComplaint(@AuthenticationPrincipal User user, @PathVariable Integer centerId, @Valid @RequestBody Complaint complaint) {
        // Pass the userId and centerId to the service along with the complaint
        complaintService.addComplaint(user.getId(), centerId, complaint);
        return ResponseEntity.status(201).body("Complaint added successfully");
    }
//YARA
    @GetMapping("/get-all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }
//YARA
    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Integer id) {
        Complaint complaint = complaintService.getComplaintById(id);
        return ResponseEntity.ok(complaint);
    }
//YARA
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Complaint> updateComplaint(@PathVariable Integer id, @Valid @RequestBody Complaint updatedComplaint, @AuthenticationPrincipal User user) {
//        updatedComplaint.setParent(user.getParent()); // Set the parent if necessary
//        Complaint complaint = complaintService.updateComplaint(id, updatedComplaint);
//        return ResponseEntity.ok(complaint);
//    }
//YARA
    @DeleteMapping("/{id}")
    public ResponseEntity deleteComplaint(@PathVariable Integer id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.status(200).body("complaint deleted successfully");
    }
    //YARA
    @GetMapping("/center")
    public ResponseEntity<List<Complaint>> getComplaintsByCenterId(@AuthenticationPrincipal User user) {
        List<Complaint> complaints = complaintService.getComplaintsByCenterId(user.getId());
        return ResponseEntity.ok(complaints);
    }
    //YARA
    @GetMapping("/my-complaint")
    public ResponseEntity<List<Complaint>> getComplaintsByUserId(@AuthenticationPrincipal User parentId) {
        List<Complaint> complaints = complaintService.getComplaintsByUserId(parentId.getId());
        return ResponseEntity.ok(complaints);
    }
    //YARA
    @PutMapping("/{complaintId}/reply")
    public ResponseEntity<String> replyToComplaint(@PathVariable Integer complaintId, @RequestBody String reply, @AuthenticationPrincipal User user) {
        complaintService.replyToComplaint(complaintId, reply, user.getId());
        return ResponseEntity.ok("Reply added successfully");
    }
}