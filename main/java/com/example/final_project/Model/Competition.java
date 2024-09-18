package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Competition {
    // [Mohammed]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 6, max = 30, message = "Name must be between 6 and 30 characters")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String name;

    @NotNull
    @PositiveOrZero(message = "Participant must be positive or zero")
    @Column(columnDefinition = "int not null")
    private int participant = 0;

    @NotNull(message = "Please enter minimum age")
    @Positive
    @Column(columnDefinition = "int not null")
    private int minAge;

    @NotNull(message = "Please enter maximum age")
    @Positive
    @Column(columnDefinition = "int not null")
    private int maxAge;

    @NotEmpty(message = "Type of competition should be not null")
    @Column(columnDefinition = "varchar(20) not null")
    private String type;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;


//====================== Relations ======================
    @ManyToMany
    @JsonIgnore
    private Set<Child> participants;
}


