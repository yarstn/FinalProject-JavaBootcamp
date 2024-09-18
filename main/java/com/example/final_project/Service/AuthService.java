package com.example.final_project.Service;


import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Notification;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    // [Mohammed]
    private final AuthRepository authRepository;
    private final NotificationService notificationService;

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }
// حذف
    public void register(User user){
        user.setRole("ADMIN");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
    }

    public void requestAccountDeletion(Integer authId){
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User not found"));

        if(!user.getId().equals(authId)){
            throw new ApiException("You don't have permission to delete this account");
        }

        user.setAccountDeletionRequested(true);
        user.setAccountDeletionRequestDate(LocalDateTime.now());
        authRepository.save(user);

        User admin = authRepository.findUserById(1).orElseThrow(() -> new ApiException("Admin not found"));
        notificationService.createNotification(
                admin,
                user,
                "Your account deletion request has been received. It will be processed in 10 days",
                Notification.NotificationType.ACCOUNT_DELETION
        );
    }

    public void cancelAccountDeletion(Integer userId){
        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found"));
        user.setAccountDeletionRequested(false);
        user.setAccountDeletionRequestDate(null);
        authRepository.save(user);

        User admin = authRepository.findUserById(1).orElseThrow(() -> new ApiException("Admin not found"));
        notificationService.createNotification(
                admin,
                user,
                "Your cancel account deletion request has been received.",
                Notification.NotificationType.CANCELED_ACCOUNT_DELETION
        );
    }


    @Scheduled(cron = "0 * * * * * ")
    public void deletionAccountAfter10Days(){
        LocalDateTime now = LocalDateTime.now();
        List<User> usersToDelete = authRepository.findAllByIsAccountDeletionRequestedAndAccountDeletionRequestDateBefore(true, now);
        for(User user : usersToDelete){
            if(user.getAccountDeletionRequestDate().plusMinutes(1).isBefore(now)){
                authRepository.delete(user);
            }
        }
    }
}

