package com.example.final_project.RepositoryTest;

import com.example.final_project.Model.Newsletter;
import com.example.final_project.Repository.NewsletterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Using real database instead of in-memory
public class NewsletterRepositoryTest {

    @Autowired
    private NewsletterRepository newsletterRepository;

    private Newsletter newsletter1, newsletter2;

    @BeforeEach
    void setUp() {
        // Initialize newsletter objects with null for ID (auto-generated)
        newsletter1 = new Newsletter(null, "test1@example.com");
        newsletter2 = new Newsletter(null, "test2@example.com");

        // Save them in the repository
        newsletterRepository.save(newsletter1);
        newsletterRepository.save(newsletter2);
    }

    @Test
    void findByEmail() {
        // Test finding a newsletter by email
        Newsletter foundNewsletter = newsletterRepository.findByEmail(newsletter1.getEmail());

        // Validate that the newsletter found by email is the same as the original one
        assertThat(foundNewsletter).isNotNull();
        assertEquals(newsletter1.getEmail(), foundNewsletter.getEmail());
        assertEquals(newsletter1.getId(), foundNewsletter.getId());
    }

    @Test
    void findById() {
        // Test finding a newsletter by ID
        Newsletter foundNewsletter = newsletterRepository.findById(newsletter1.getId()).orElse(null);

        // Validate that the newsletter found by ID is the same as the original one
        assertThat(foundNewsletter).isNotNull();
        assertEquals(newsletter1.getId(), foundNewsletter.getId());
        assertEquals(newsletter1.getEmail(), foundNewsletter.getEmail());
    }
}
