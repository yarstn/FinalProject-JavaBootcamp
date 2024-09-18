package com.example.final_project.Model;

import lombok.Getter;
import lombok.Setter;


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

import java.util.Date;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor

// Abdulaziz

public class Program {
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

    @NotNull(message = "price should not be empty!")
    @PositiveOrZero(message = "price should be positive number or zero!")
    @Column(columnDefinition = "double not null")
    private double price;

    @NotNull(message = "capacity should not be empty!")
    @Positive(message = "capacity should be positive number!")
    @Column(columnDefinition = "int not null")
    private int capacity;

    @NotNull(message = "Max age should not be empty!")
    @Positive(message = "Max age should be positive number!")
    @Column(columnDefinition = "int not null")
    private int maxAge;

    // +
    @NotNull(message = "Total Sessions is required")
    @Min(value = 0, message = "Total sessions must be zero or positive")
    private Integer totalSessions;

    @NotNull(message = "Max age should not be empty!")
    @Positive(message = "Max age should be positive number!")
    @Column(columnDefinition = "int not null")
    private int minAge;

    @NotEmpty(message = "Address cannot be Empty!")
    @Size(min = 2,max = 40,message = "Address length must be more than '4' and less than '40'!")
    @Column(columnDefinition = "varchar(40) not null")
    private String address;

    @Pattern(regexp = "^(open|close)$")
    @Column(columnDefinition = "varchar(15)")
    private String status = "open";

    @NotNull(message = "duration should not be empty!")
    @Positive(message = "duration should be positive number!")
    @Column(columnDefinition = "int not null")
    private int durationByWeeks;

    @Column(columnDefinition = "double")
    private double programFinancialReturn=0;

    @Column(columnDefinition = "int")
    private int numOfChildrensInTheProgram=0;

    @NotNull(message = "StartDate should be not empty!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @NotNull(message = "EndDate should be not empty!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;


    //====================== Relations ======================

    @ManyToOne
    @JsonIgnore
    private Center center;

    @ManyToMany
    @JsonIgnore
    private Set<Child> child;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private Set<ChildProgress> progresses;
}
