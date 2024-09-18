package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "child_progress")
public class ChildProgress {
    // [Mohammed]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


//    @NotEmpty(message = "Current stage should be empty")
//    @Size(min = 5, max = 30, message = "Current stage must be between 5 and 30 characters")
//    @Column(columnDefinition = "varchar(30) not null")
    private String currentStage;

//    @NotEmpty(message = "Progress details should be empty")
//    @Size(min = 5, max = 255, message = "Progress details must be between 5 and 255 characters")
//    @Column(columnDefinition = "varchar(255) not null")
    private String progressDetails;

//    @UpdateTimestamp
//    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime lastUpdate;


//    @PositiveOrZero(message = "Attendance percentage must be zero or positive")
//    @Max(value = 100, message = "Attendance percentage cannot exceed 100")
//    @Column(columnDefinition = "not null")
    private double attendancePercentage;

//    @PositiveOrZero(message = "Progress level must be zero or positive")
//    @Max(value = 100, message = "Progress level cannot exceed 100")
//    @Column(columnDefinition = "not null")
    private double progressLevel;
//
//    @Size(max = 100, message = "Max length 100")
//    @Column(columnDefinition = "varchar(100)")
    private String note;

//    @Min(value = 0, message = "Completed sessions must be zero or positive")
    private Integer completedSessions;

//    @Min(value = 0, message = "Total sessions must be zero or positive")
    private Integer totalSessions;

//    @PositiveOrZero(message = "Rating must be zero or positive")
//    @Min(value = 0, message = "Rating cannot exceed 0")
//    @Max(value = 5, message = "Rating cannot exceed 5")
    private double rating;

    @ManyToOne
    @JsonIgnore
//    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private Child child;

    @ManyToOne
    @JsonIgnore
//    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;
}
