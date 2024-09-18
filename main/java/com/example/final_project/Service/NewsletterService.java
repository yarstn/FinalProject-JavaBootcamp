package com.example.final_project.Service;

import com.example.final_project.Model.Newsletter;
import com.example.final_project.Repository.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class NewsletterService {
    //ALL BY YARA
    private NewsletterRepository newsletterRepository;

    @Autowired
    public NewsletterService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    public boolean subscribe(String email) {
        // Check if email is already subscribed
        if (newsletterRepository.findByEmail(email) != null) {
            return false;
        }
        // Subscribe the email
        Newsletter newsletter = new Newsletter();
        newsletter.setEmail(email);
        newsletterRepository.save(newsletter);
        return true;
    }
    // Unsubscribe method
    public boolean unsubscribe(String email) {
        Newsletter newsletter = newsletterRepository.findByEmail(email);
        if (newsletter != null) {
            newsletterRepository.delete(newsletter);
            return true;
        }
        return false;
    }
    // Method to get all subscribers
    public List<String> getAllSubscribers() {
        List<Newsletter> subscribers = newsletterRepository.findAll();
        return subscribers.stream().map(Newsletter::getEmail).collect(Collectors.toList());
    }
}

