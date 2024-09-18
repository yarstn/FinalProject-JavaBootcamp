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

import java.time.LocalDate;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor

// Abdulaziz

public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "certificate description should not be empty!")
    @Size(max = 70,message = "maximum length of certificate description is '70'!")
    @Column(columnDefinition = "varchar(70) not null")
    private String certificateDescription;

    @NotEmpty(message = "program or competition should not be empty!")
    @Size(max = 50,message = "maximum length of program OR Competition is '50'!")
    @Column(columnDefinition = "varchar(50) not null")
    private String program_Competition;

    @NotNull(message = "graduation date should not be empty!")
    @Column(columnDefinition = "date not null")
    private LocalDate graduation_date;

    @NotNull(message = "graduation date should not be empty!")
    @Size(max = 50,message = "maximum length of center name is '50'!")
    @Column(columnDefinition = "varchar(50) not null")
    private String centerName;


    @ManyToOne
    @JsonIgnore
    private Child child;
}
