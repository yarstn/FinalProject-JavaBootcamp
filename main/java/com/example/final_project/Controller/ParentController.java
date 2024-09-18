package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CenterService;
import com.example.final_project.Service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/parent")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;
    private final CenterService centerService;

    //YARA Get all parents
    @GetMapping("/get-all")
    public ResponseEntity getAllParents() {
        return ResponseEntity.ok(parentService.getAllParents());
    }

    // YARA Get parent by ID
//    @GetMapping("/{id}")
//    public ResponseEntity getParentById(@AuthenticationPrincipal User user, @PathVariable Integer id) {
//        Parent parent = parentService.getParentById(id)
//                .orElseThrow(() -> new RuntimeException("Parent not found with ID: " + id));
//        return ResponseEntity.ok(parent);
//    }

    //YARA Add a new parent
    @PostMapping("/register")
    public ResponseEntity addParent(@Valid @RequestBody ParentDTO parentDTO) {
       parentService.addParent(parentDTO);
        return ResponseEntity.status(200).body("parent added");
    }

    //YARA Update an existing parent
    @PutMapping("/update")
    public ResponseEntity updateParent(@AuthenticationPrincipal User user, @RequestBody ParentDTO parentDTO) {
        parentService.updateParent(user.getId(), parentDTO);
        return ResponseEntity.status(200).body("parent updated " );
    }

    //YARA Delete a parent by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteParent(@PathVariable Integer id) {
        parentService.delete(id);
        return ResponseEntity.status(200).body("user deleted successfully");
    }//YARA
    @GetMapping("/my-account")
    public ResponseEntity getMyAccount(@AuthenticationPrincipal User user) {
        Parent parent = parentService.getParentByUserId(user.getId());
        return ResponseEntity.ok(parent);
    }
    //YARA
    @GetMapping("/my-children")
    public ResponseEntity getMyChildren(@AuthenticationPrincipal User user) {
        Parent parent = parentService.getParentByUserId(user.getId());
        Set<Child> children = parentService.getChildrenByParentId(parent.getId());
        return ResponseEntity.ok(children);
    }
    //YARA Endpoint to like or dislike a center
    @PostMapping("/like-center/{centerId}")
    public ResponseEntity<String> likeCenter(@AuthenticationPrincipal User user,
                                             @PathVariable Integer centerId,
                                             @RequestParam Integer likeStatus) {
        parentService.likeCenter(user.getId(), centerId, likeStatus);
        String response = likeStatus == 1 ? "Center liked" : "Center disliked";
        return ResponseEntity.ok(response);
    }

    //YARA Endpoint to get all centers liked by the parent
    @GetMapping("/liked-centers")
    public ResponseEntity<Set<Center>> getLikedCenters(@AuthenticationPrincipal User user) {
        Set<Center> likedCenters = parentService.getLikedCenters(user.getId());
        return ResponseEntity.ok(likedCenters);
    }
//YARA
    @PostMapping("/add-rate/{centerid}/{rate}")
    public ResponseEntity addRateToCenter(@AuthenticationPrincipal User user, @PathVariable Integer centerid, @PathVariable Integer rate) {
        centerService.addRate(user.getId(), centerid, rate);
        return ResponseEntity.status(201).body("rate added");
    }
}