package com.example.final_project.Repository;

import com.example.final_project.Model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
//    Optional<Complaint> findComplaintById(Integer integer);
    List<Complaint> findByCenterId(Integer centerId);
    List<Complaint> findByParentId(Integer parentId);


}
