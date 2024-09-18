package com.example.final_project.Repository;

import com.example.final_project.Model.Notification;
import com.example.final_project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    // [Mohammed]
    Optional<Notification> findNotificationById(Integer id);
    Optional<List<Notification>> findAllByUser(User user);
    Optional<List<Notification>> findAllByUserAndIsReadFalse(User user);
    Optional<List<Notification>> findAllByUserAndIsReadTrue(User user);
 }
