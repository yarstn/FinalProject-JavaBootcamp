package com.example.final_project.Controller;

import com.example.final_project.Model.Child;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/child")
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;

    //YARA Get all children
    @GetMapping("/get-all")
    public ResponseEntity<List<Child>> getAllChildren() {

        return ResponseEntity.ok(childService.getAllChildren());
    }

    //YARA Add a new child
    @PostMapping("/register")
    public ResponseEntity addChild(@AuthenticationPrincipal User user, @Valid @RequestBody Child child) {
        childService.addChild(user.getId(), child);
        return ResponseEntity.status(200).body("child added successfully");
    }

    //YARA Update an existing child
    @PutMapping("/update/{id}")
    public ResponseEntity updateChild(@AuthenticationPrincipal User user, @PathVariable Integer id, @Valid @RequestBody Child updatedChild) {
        childService.updateChild(user.getId(), id, updatedChild);
        return ResponseEntity.status(200).body("child updated successfully");
    }

//YARA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChild(@AuthenticationPrincipal User user,
                                              @PathVariable Integer id) {
        // Call the service to delete the child
        childService.deleteParent(user.getId(), id);
        return ResponseEntity.ok("Child deleted successfully.");
    }
    //YARA
    @PostMapping("/{childId}/program/{programId}/apply")
    public ResponseEntity<String> applyChildToProgram(@AuthenticationPrincipal User user, @PathVariable Integer childId, @PathVariable Integer programId) {
        childService.addChildToProgram(user.getId(), childId, programId);
        return ResponseEntity.status(200).body("Child successfully applied to the program.");
    }

    // YARA Endpoint to get all programs associated with a parent's children
    @GetMapping("/my-programs")
    public ResponseEntity<List<Program>> getAllProgramsForParent(@AuthenticationPrincipal User user) {
        List<Program> programs = childService.getAllProgramsForParent(user.getId());
        return ResponseEntity.ok(programs);
    }
    // YARA Endpoint to cancel a program for a child
    @DeleteMapping("/{childId}/cancel-program/{programId}")
    public ResponseEntity<String> cancelProgram(@AuthenticationPrincipal User user, @PathVariable Integer childId, @PathVariable Integer programId) {
        String result = childService.cancelProgram(user.getId(), childId, programId);
        return ResponseEntity.ok(result);
    }
}