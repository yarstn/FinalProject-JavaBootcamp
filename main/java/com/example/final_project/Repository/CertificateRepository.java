package com.example.final_project.Repository;

import com.example.final_project.Model.Certificate;
import com.example.final_project.Model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

//    Optional<Certificate> findCertificateById(Integer id);

    List<Certificate> findCertificateByChild(Child child);
}
