package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.User;
import com.example.final_project.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController {
    // [Mohammed]
    private final AuthService authService;

    // [Mohammed]
    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(authService.getAllUsers());
    }

    // [Mohammed]
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user){
        authService.register(user);
        return ResponseEntity.ok(new ApiResponse("User added successfully"));
    }

    // [Mohammed] + End-Point
    @DeleteMapping("/request-account-deletion")
    public ResponseEntity<ApiResponse> requestAccountDeletion(@AuthenticationPrincipal User user){
        authService.requestAccountDeletion(user.getId());
        return ResponseEntity.ok(new ApiResponse("Successfully request deletion account"));
    }

    // [Mohammed] + End-Point
    @PostMapping("/cancel-request-account-deletion")
    public ResponseEntity<ApiResponse> cancelAccountDeletion(@AuthenticationPrincipal User user){
        authService.cancelAccountDeletion(user.getId());
        return ResponseEntity.ok(new ApiResponse("Successfully cancelled deletion account request"));
    }
}
