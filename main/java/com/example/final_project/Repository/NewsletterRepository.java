package com.example.final_project.Repository;

import com.example.final_project.Model.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Integer> {
    Newsletter findByEmail(String email);
}
