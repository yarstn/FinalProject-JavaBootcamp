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

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor

// Abdulaziz

public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title cannot be Empty!")
    @Size(min = 5,max = 30,message = "Title length must be more than '5' and less than '30'!")
    @Column(columnDefinition = "varchar(30) not null")
    private String title;

    @NotEmpty(message = "Description cannot be Empty!")
    @Size(min = 20,max = 550,message = "Description length must be more than '15' and less than '550'!")
    @Column(columnDefinition = "varchar(550) not null")
    private String description;

    @NotNull(message = "Days duration should not be empty!")
    @Positive(message = "days duration should be positive")
    @Max(value = 10,message = "Maximum days duration is '10'!")
    private int daysDuration;

    @Column(columnDefinition = "double")
    private double price = 0;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date ")
    private LocalDate publishDate; // Determined by admin

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('APPROVED', 'IN_PROGRESS', 'REJECTED') not null default 'IN_PROGRESS'")
    private Status status = Status.IN_PROGRESS;   // +

    public enum Status{
        APPROVED,
        IN_PROGRESS,
        REJECTED
    }

    // ========= relations =====
    @ManyToOne
    @JsonIgnore
    private Center center;
}
