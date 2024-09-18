package com.example.final_project.DTO;

import com.example.final_project.Model.Center;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

// Abdulaziz
public class CenterDTO {
    private Integer id;

    @NotEmpty(message = "Username should be not empty")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotEmpty(message = "Password should be not empty")
    @Size(min = 6, max = 255)
    private String password;


    @NotEmpty(message = "Name should be not empty")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @NotEmpty(message = "Role should be not empty")
    private String role = "CENTER";

    @NotEmpty(message = "Phone number should not be empty!")
    @Size(min = 10,max = 10,message = "teacher phone number should be '10' digits")
    @Pattern(regexp = "^05\\d*$",message = "Phone number must start with '05' !")
    private String phoneNumber;


    @NotEmpty(message = "Address cannot be Empty!")
    @Size(min = 2,max = 40,message = "Address length must be more than '4' and less than '40'!")
    private String address;

    @NotEmpty(message = "description cannot be Empty!")
    @Size(min = 2,max = 500,message = "description length must be more than '4' and less than '50'!")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;


    @NotEmpty(message = "licence cannot be Empty!")
    @Size(min = 8,max = 8,message = "licence number should be '8' digits")
    @Column(columnDefinition = "varchar(8) not null")
    private String licence;

    private Center.Status status = Center.Status.IN_PROGRESS;

    @NotEmpty(message = "Activity Type cannot be Empty!")
    @Pattern(regexp = "^(Sport|Academic|Cultural|Draw)$")
    private String activityType;


}
