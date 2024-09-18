package com.example.final_project.Service;


import com.example.final_project.API.ApiException;
import com.example.final_project.Model.*;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final AuthRepository authRepository;
    private final ParentReposotiry parentReposotiry;
    private final ChildRepository childRepository;
    private final ProgramRepository programRepository;

    //Abdulaziz
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    //Abdulaziz
    public List<Certificate> childCertificates(int userId, int childId) {

        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        Parent parent = parentReposotiry.findParentById(user.getId())
                .orElseThrow(() -> new ApiException("parent not found"));

        Child child = childRepository.findChildById(childId)
                .orElseThrow(() -> new ApiException("child not found"));

        if (child.getParent() != parent) {
            throw new ApiException("Parent and child are not the same");
        }

        return certificateRepository.findCertificateByChild(child);
    }

    //Abdulaziz
    public Certificate issueCertificate(Integer childId, Integer programId, Certificate certificateDetails) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ApiException("Child not found"));

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ApiException("Program not found"));

        // Ensure the program has ended
        if (LocalDate.now().isBefore(program.getEndDate())) {
            throw new ApiException("Program has not ended yet");
        }

        // Set certificate details
        certificateDetails.setChild(child);
        certificateDetails.setProgram_Competition(program.getTitle());
        certificateDetails.setCenterName(program.getCenter().getUser().getName());
        certificateDetails.setGraduation_date(program.getEndDate());

        // Save and return the certificate
        return certificateRepository.save(certificateDetails);
    }


}