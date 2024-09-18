package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    // [Mohammed]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Notification type cannot be null")
    @Column(columnDefinition = "enum('CANCELED_ACCOUNT_DELETION', 'ACCOUNT_DELETION', 'CENTER_REQUEST_REGISTRATION', 'REQUEST_PARTICIPATION', 'CENTER_TO_ADMIN',  'ADMIN_TO_CENTER', 'ADMIN_TO_PARENT') not null")
    private NotificationType notificationType;


    @NotEmpty(message = "Message should be not empty")
    @Size(min = 4, max = 500, message = "Message should be between 4 and 500 characters")
    @Column(columnDefinition = "text not null")
    private String message;

    @Column(columnDefinition = "boolean not null default false")
    private boolean isRead = false;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private User sender;

    @ManyToOne
    @JsonIgnore
    private User receiver;


    public enum NotificationType{
        ADMIN_TO_PARENT,
        ADMIN_TO_CENTER,
        CENTER_TO_ADMIN,
        REQUEST_PARTICIPATION,
        CENTER_REQUEST_REGISTRATION,
        ACCOUNT_DELETION,
        CANCELED_ACCOUNT_DELETION
    }
}
