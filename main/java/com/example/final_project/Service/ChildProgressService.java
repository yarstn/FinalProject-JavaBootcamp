package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.ChildProgress;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.ChildProgressRepository;
import com.example.final_project.Repository.ChildRepository;
import com.example.final_project.Repository.ProgramRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildProgressService {
    // [Mohammed]
    private final ChildProgressRepository childProgressRepository;
    private final ProgramRepository programRepository;
    private final ChildRepository childRepository;
    private final AuthRepository authRepository;

    public List<ChildProgress> getAllChildProgress(){
        return childProgressRepository.findAll();
    }

    public List<ChildProgress> getChildProgressByProgramId(Integer program_id){
        return childProgressRepository.findAllByProgramId(program_id).orElseThrow(() -> new ApiException("No progress found for this program"));
    }

    public List<ChildProgress> getAllChildProgressByChildId(Integer childId){
       return childProgressRepository.findAllByChildId(childId).orElseThrow(() -> new ApiException("No progress found for this child"));
    }

    public void createChildProgresss(Integer authId, ChildProgress childProgress){
        Program program = programRepository.findProgramById(childProgress.getProgram().getId()).orElseThrow(() -> new ApiException("Not found program"));
        Child child = childRepository.findChildById(childProgress.getChild().getId()).orElseThrow(() -> new ApiException("Not found child"));
        User center = authRepository.findUserById(authId).orElseThrow(() -> new ApiException("Not found center"));
        if(!program.getCenter().equals(center.getCenter())){
            throw new ApiException("You don't have permission to modify this child progress");
        }
        childProgressRepository.save(childProgress);
    }

    public void createChildProgress(Child child , Program program){
        ChildProgress childProgress = new ChildProgress();
        childProgress.setProgram(program);
        childProgress.setChild(child);
        childProgress.setCurrentStage("Not Started");
        childProgress.setProgressDetails("Registered in the program");
        childProgress.setLastUpdate(LocalDateTime.now());
        childProgress.setAttendancePercentage(0.0);
        childProgress.setProgressLevel(0.0);
        childProgress.setNote("");
        childProgress.setCompletedSessions(0);
        childProgress.setTotalSessions(program.getTotalSessions());
        childProgress.setRating(0.0);
        childProgressRepository.save(childProgress);
    }

    @Transactional
    public void updateChildProgress(Integer authId, Integer progressId, ChildProgress updateChildProgress){
        ChildProgress childProgress = childProgressRepository.findChildProgressById(progressId).orElseThrow(() -> new ApiException("Not found progress"));
        Program program = programRepository.findProgramById(childProgress.getProgram().getId()).orElseThrow(() -> new ApiException("Not found program"));
        User center = authRepository.findUserById(authId).orElseThrow(() -> new ApiException("Not found center"));
        Child child = childRepository.findChildById(childProgress.getChild().getId()).orElseThrow(() -> new ApiException("Not found child"));
        if(!program.getCenter().equals(center.getCenter())){
            throw new ApiException("You don't have permission to modify this child progress");
        }

        if(!child.getPrograms().contains(program)){
            throw new ApiException("Child is not registered in this program");
        }

        childProgress.setCurrentStage(updateChildProgress.getCurrentStage());
        childProgress.setProgressDetails(updateChildProgress.getProgressDetails());
        childProgress.setCompletedSessions(updateChildProgress.getCompletedSessions());
        childProgress.setNote(updateChildProgress.getNote());
        childProgress.setTotalSessions(program.getTotalSessions());

        double progressLevel = calculateProgressLevel(childProgress.getCompletedSessions(), childProgress.getTotalSessions());
        double attendancePercentage = calculateAttendancePercentage(childProgress.getCompletedSessions(), childProgress.getTotalSessions());
        double rating = calculateRating(childProgress.getCompletedSessions(), childProgress.getTotalSessions());

        childProgress.setProgressLevel(progressLevel);
        childProgress.setAttendancePercentage(attendancePercentage);
        childProgress.setRating(rating);

        childProgressRepository.save(childProgress);
    }

    public void deleteChildProgress(Integer authId, Integer progressId){
        ChildProgress childProgress = childProgressRepository.findChildProgressById(progressId).orElseThrow(() -> new ApiException("Not found progress"));
        Program program = programRepository.findProgramById(childProgress.getProgram().getId()).orElseThrow(() -> new ApiException("Not found program"));
        User center = authRepository.findUserById(authId).orElseThrow(() -> new ApiException("Not found center"));
        if(!program.getCenter().equals(center.getCenter())){
            throw new ApiException("You don't have permission to modify this child progress");
        }
        childProgressRepository.delete(childProgress);
    }

    public double calculateRating(int completedSessions, int totalSessions){
        if(totalSessions == 0) return 0.0;
        double rating = (double) completedSessions / totalSessions * 5;
        return Math.min(Math.max(rating, 0.0), 5.0);
    }

    private double calculateProgressLevel(int camplatedSessions, int totalSessions){
        if(totalSessions == 0) return 0.0;
        return Math.min((double) camplatedSessions / totalSessions * 100, 100.0);
    }

    private double calculateAttendancePercentage(int camplatedSessions, int totalSessions){
        if(totalSessions == 0) return 0.0;
        return Math.min((double) camplatedSessions / totalSessions * 100, 100.0);
    }
}
