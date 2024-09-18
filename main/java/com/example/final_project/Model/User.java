package com.example.final_project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    // [Mohammed]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username should be not empty")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String username;

    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Please enter a valid email")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotEmpty(message = "Password should be not empty")
    @Size(min = 6, max = 255)
    @Column(columnDefinition = "varchar(255) not null")
    private String password;

    // [Abdulaziz]
    @NotEmpty(message = "Phone number should not be empty!")
    @Size(min = 10,max = 10,message = "teacher phone number should be '10' digits")
    @Pattern(regexp = "^05\\d*$",message = "Phone number must start with '05' !")
    private String phoneNumber;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "Role should be not empty")
    @Column(columnDefinition = "enum('ADMIN', 'PARENT', 'CENTER') not null")
    private String role;

    private boolean isAccountDeletionRequested = false;
    private LocalDateTime accountDeletionRequestDate;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt;

//====================== Relations ======================
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Parent parent;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Center center;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Notification> senderNotifications;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<Notification> receiverNotifications;

//====================== User Details ====================== 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}