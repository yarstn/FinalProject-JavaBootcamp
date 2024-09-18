package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ParentReposotiry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentReposotiry parentRepository;
    private final AuthRepository authRepository;
    private final CenterRepository centerRepository;

    //ALL BY YARA
    // Get all parents AdminParent
    public List<User> getAllParents() {
        return authRepository.findUserByRole("PARENT");
    }

    // Get parent by ID
    public Parent getParentById(Integer id) {
        return parentRepository.findParentById(id).orElseThrow(() -> new ApiException("Parent Not Found"));
    }

    public void addParent(ParentDTO parentDTO) {
        User user = new User();
        user.setUsername(parentDTO.getUsername());
        user.setPassword(parentDTO.getPassword());
        user.setEmail(parentDTO.getEmail());
        user.setName(parentDTO.getName());
        user.setRole(parentDTO.getRole());
        user.setPhoneNumber(parentDTO.getPhoneNumber());

        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);

        Parent parent = new Parent();
        parent.setId(null);

        user.setParent(parent);
        parent.setUser(user);

        authRepository.save(user);
        parentRepository.save(parent); // Return the saved Parent
    }

    // Update an existing parent
    public void updateParent(Integer id, ParentDTO parentDTO) {
        Parent existingParent = parentRepository.findParentById(id)
                .orElseThrow(() -> new ApiException("Parent not found with ID: " + id));

        User user = existingParent.getUser();
        user.setUsername(parentDTO.getUsername());
        user.setEmail(parentDTO.getEmail());
        user.setName(parentDTO.getName());
        user.setPhoneNumber(parentDTO.getPhoneNumber());

        if (parentDTO.getPassword() != null && !parentDTO.getPassword().isEmpty()) {
            String hash = new BCryptPasswordEncoder().encode(parentDTO.getPassword());
            user.setPassword(hash);
        }

        authRepository.save(user);
    }



    public void delete(Integer authId){
        User user  = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User not found"));
        authRepository.delete(user);
    }


    public Parent getParentByUserId(Integer userId) {
        return parentRepository.findParentByUser_Id(userId)
                .orElseThrow(() -> new ApiException("Parent details not found for the user"));
    }
    public Set<Child> getChildrenByParentId(Integer parentId) {
        Parent parent = parentRepository.findParentById(parentId)
                .orElseThrow(() -> new ApiException("Parent not found"));
        return parent.getChildren();
    }
    // Like or dislike a center
    public void likeCenter(Integer parentId, Integer centerId, Integer likeStatus) {
        Parent parent = parentRepository.findParentById(parentId)
                .orElseThrow(() -> new ApiException("Parent not found"));
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new ApiException("Center not found"));

        Set<Center> likedCenters = parent.getLikedCenters();

        if (likeStatus == 1) {  // Like the center
            likedCenters.add(center);
        } else if (likeStatus == 0) {  // Dislike the center (remove from liked list)
            likedCenters.remove(center);
        }

        parent.setLikedCenters(likedCenters);
        parentRepository.save(parent);
    }

    // Get all centers the parent likes
    public Set<Center> getLikedCenters(Integer parentId) {
        Parent parent = parentRepository.findParentById(parentId)
                .orElseThrow(() -> new ApiException("Parent not found"));
        return parent.getLikedCenters();
    }
}