package com.example.final_project.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Child {
    //YARA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    @Column(name = "pic_url")
    private String picUrl;

    @NotNull(message = "Age cannot be null")
    @Column(nullable = false)
    private Integer age;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime joinDate;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updateDate;


    @NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "^(ذكر|انثى)$", message = "Gender must be either 'ذكر' or 'انثى'")
    @Column(nullable = false)
    private String gender; // Gender validation

    //RELATION
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    @JsonIgnore
    private Parent parent; // Reference to Parent

    @ManyToMany
    @JoinTable(name = "child_program_subscription", joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "program_id"))
    private Set<Program> programs; // Many-to-many relation with Program

    @ManyToMany(mappedBy = "participants", cascade = CascadeType.ALL)
    private Set<Competition> competitions;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private Set<ChildProgress> childProgresses;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private Set<Certificate> certificates;
}