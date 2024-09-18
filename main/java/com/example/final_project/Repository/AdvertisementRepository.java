package com.example.final_project.Repository;

import com.example.final_project.Model.Advertisement;
import com.example.final_project.Model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

    Optional<Advertisement> findAdvertisementById(int id);

    List<Advertisement> findAdvertisementByCenter(Center center);
}
