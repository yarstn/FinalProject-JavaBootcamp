package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.DTO.CenterDTO;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CenterService;
import com.example.final_project.Service.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/center")
public class CenterController {

    private final ProgramService programService;
    private final CenterService centerService;

    //Abdulaziz
    @GetMapping("/get-all-centers")
    public ResponseEntity getAllCenters(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.getAllCenters());
    }

    //Abdulaziz
    @PostMapping("/center-register")
    public ResponseEntity centerRegister(@Valid @RequestBody CenterDTO centerDTO){
        centerService.centerRegister(centerDTO);
        return ResponseEntity.status(200).body("Center registered successfully");
    }

    //Abdulaziz
    @PutMapping("/update-center")
    public ResponseEntity updateCenter(@AuthenticationPrincipal User user, @Valid @RequestBody CenterDTO centerDTO){
        centerService.updateCenter(user.getId(), centerDTO);
        return ResponseEntity.status(200).body("Center updated successfully");
    }

    //Abdulaziz
    @DeleteMapping("/delete-center/{centerid}")
    public ResponseEntity deleteCenter(@PathVariable int centerid, @AuthenticationPrincipal User user){
        centerService.deleteCenter(user.getId(),centerid);
        return ResponseEntity.status(200).body("Center deleted successfully");
    }

    //Abdulaziz
    @GetMapping("/Center-Account")
    public ResponseEntity showMyCenterAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.showMyCenterAccount(user.getId()));
    }

    //Abdulaziz
    @PutMapping("/change-password/{oldpassword}/{newpassword}")
    public ResponseEntity changePassword( @AuthenticationPrincipal User user,@PathVariable String oldpassword, @PathVariable String newpassword){
        centerService.changePassword(user.getId(),oldpassword,newpassword);
        return ResponseEntity.status(200).body("Password changed successfully");
    }

    //Abdulaziz
    @GetMapping("/display-total-center-financial-returns")
    public ResponseEntity displayCenterFinancialReturn(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.displayCenterFinancialReturns(user.getId()));
    }

    //Abdulaziz
    @GetMapping("/display-total-number-of-joind-childrens")
    public ResponseEntity displayTotalNumberOfJoindChildren(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.displayCenterNumberOfChild(user.getId()));
    }

    //Abdulaziz
    @GetMapping("/display-total-number-center-program")
    public ResponseEntity displayTotalNumberOfCenterProgram(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.getTotalNumberOfCenterPrograms(user.getId()));
    }

    //Abdulaziz
    @PutMapping("/expand-program/{programid}/{enddate}")
    public ResponseEntity expandProgram(@AuthenticationPrincipal User user,@PathVariable Integer programid, @PathVariable LocalDate enddate){
        programService.expandProgram(user.getId(),programid,enddate);
        return ResponseEntity.status(200).body("program expand successfully");
    }

    //Abdulaziz
    @GetMapping("/display-approved-centers")
    public ResponseEntity displayApprovedCenters(){
        return ResponseEntity.status(200).body(centerService.displayApprovedCenters());
    }

    //Abdulaziz
    @GetMapping("/display-childrens-by-program/{programid}")
    public ResponseEntity displayChildrensByprogram(@AuthenticationPrincipal User user, @PathVariable int programid){
        return ResponseEntity.ok(programService.displayChildrenInTheProgram(user.getId(), programid));
    }


    // [ Mohammed ] +[End-Point]
    @PutMapping("/approve-center-registration/{centerId}")
    public ResponseEntity<ApiResponse> approveCenterRegistration(@PathVariable Integer centerId) {
        centerService.approveCenterRegistration(centerId);
        return ResponseEntity.ok(new ApiResponse("Notification approved successfully"));
    }

    // [ Mohammed ] +[End-Point]
    @PutMapping("/reject-center-registration/{centerId}/{rejectionReason}")
    public ResponseEntity<ApiResponse> rejectCenterRegistration(@PathVariable Integer centerId, @PathVariable String rejectionReason) {
        centerService.rejectCenterRegistration(centerId, rejectionReason);
        return ResponseEntity.ok(new ApiResponse("Notification rejected successfully"));
    }


}
