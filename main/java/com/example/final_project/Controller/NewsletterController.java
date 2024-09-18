package com.example.final_project.Controller;

import com.example.final_project.Service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

    @Autowired
    private NewsletterService newsletterService;
//YARA
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToNewsletter(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        if (newsletterService.subscribe(email)) {
            return new ResponseEntity<>("Subscription successful!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email is already subscribed.", HttpStatus.BAD_REQUEST);
        }
    }
    // YARA Unsubscribe endpoint
    @DeleteMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String email) {
        if (newsletterService.unsubscribe(email)) {
            return new ResponseEntity<>("Unsubscription successful!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email not found in the subscription list.", HttpStatus.NOT_FOUND);
        }
    }
    //YARA Get all subscribers endpoint
    @GetMapping("/subscribers")
    public ResponseEntity<List<String>> getAllSubscribers() {
        List<String> subscribers = newsletterService.getAllSubscribers();
        return new ResponseEntity<>(subscribers, HttpStatus.OK);
    }

}
