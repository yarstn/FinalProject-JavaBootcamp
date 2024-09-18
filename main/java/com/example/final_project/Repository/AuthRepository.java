package com.example.final_project.Repository;

import com.example.final_project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
    // [Mohammed]
    Optional<User> findUserById(Integer id);
    Optional<User> findByUsername(String username);
//    List<User> findAllByPendingDeletionTrue();
    List<User> findUserByRole(String role);
    List<User> findAllByIsAccountDeletionRequestedAndAccountDeletionRequestDateBefore(boolean isAccountDeletionRequested, LocalDateTime dateTime);

    User searchUserById(Integer id);

}
