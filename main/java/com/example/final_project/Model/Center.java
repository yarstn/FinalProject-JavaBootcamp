package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

// Abdulaziz

public class Center {
    @Id
    private Integer id;

    @NotEmpty(message = "description cannot be Empty!")
    @Size(min = 2,max = 500,message = "description length must be more than '4' and less than '50'!")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotEmpty(message = "Address cannot be Empty!")
    @Size(min = 2,max = 40,message = "Address length must be more than '4' and less than '40'!")
    @Column(columnDefinition = "varchar(40) not null")
    private String address;


    @NotEmpty(message = "licence cannot be Empty!")
    @Size(min = 8,max = 8,message = "licence number should be '8' digits")
    @Column(columnDefinition = "varchar(8) not null")
    private String licence;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('APPROVED', 'IN_PROGRESS', 'REJECTED') not null default 'IN_PROGRESS'")
    private Status status = Status.IN_PROGRESS;   // +

    @NotEmpty(message = "Activity Type cannot be Empty!")
    @Pattern(regexp = "^(Sport|Academic|Cultural|Draw)$")
    @Column(columnDefinition = "varchar(15) not null")
    private String activityType;

    @Column(columnDefinition = "double")
    private double centerFinancialReturns = 0;

    @Column(columnDefinition = "int")
    private int numOfChildrensInTheCenter= 0;

    @Column(columnDefinition = "double")
    private double rate = 0;

    //====================== Relations ======================

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "center")
    private Set<Comment> comments; // One center can have many comments

    @OneToMany(mappedBy = "center")
    private Set<Complaint> complaints; // One center can have many complaints

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Program> program;

    @OneToMany(mappedBy = "center")

    private Set<Advertisement> advertisements;

    public enum Status{
        APPROVED,
        IN_PROGRESS,
        REJECTED
    }
}