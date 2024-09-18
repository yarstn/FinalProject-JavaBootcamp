package com.example.final_project.Service;


import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final AuthRepository authRepository;
    private final CenterRepository centerRepository;

    //Abdulaziz
    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    //Abdulaziz
    public void addProgram(int centerId, Program program) {
        Center center = centerRepository.findCenterById(centerId)
                .orElseThrow(() -> new ApiException("center not found"));
//
        if (!center.getStatus().equals(Center.Status.APPROVED)){
            throw new ApiException("center not allowed to add program, center status is " + center.getStatus());
        }

        program.setCenter(center);
        programRepository.save(program);
    }

    //Abdulaziz
    public void updateProgram(int centerId,int programId ,Program program) {

        Center center = centerRepository.findCenterById(centerId).orElseThrow(() -> new ApiException("center not found"));

        Program oldProgram = programRepository.findProgramById(programId).orElseThrow(() -> new ApiException("program not found"));

        //check the match between center and entered program
        if (center.getProgram().contains(program)){
            throw new ApiException("Center not match with entered program");
        }
        oldProgram.setTitle(program.getTitle());
        oldProgram.setDescription(program.getDescription());
        oldProgram.setAddress(program.getAddress());
        oldProgram.setMaxAge(program.getMaxAge());
        oldProgram.setMinAge(program.getMinAge());
        oldProgram.setDurationByWeeks(program.getDurationByWeeks());
        oldProgram.setEndDate(program.getEndDate());
        oldProgram.setTotalSessions(program.getTotalSessions());
        oldProgram.setStartDate(oldProgram.getStartDate());
        oldProgram.setProgramFinancialReturn(oldProgram.getProgramFinancialReturn());


        oldProgram.setCenter(center);
        programRepository.save(oldProgram);

    }

    //Abdulaziz
    public List<Program> displayAllMyPrograms(int userId) {
        User user = authRepository.findUserById(userId).orElseThrow(() -> new ApiException("user not found"));

        Center center = centerRepository.findCenterById(user.getCenter().getId()).orElseThrow(() -> new ApiException("center not found"));

        return programRepository.findProgramByCenter(center); //called by parent
    }

    //Yara
    // Search for programs by title
    public List<Program> searchProgramsByTitle(String title) {
        return programRepository.findByTitleContainingIgnoreCase(title);
    }


    //Abdulaziz
    // display programs when enter canter page
    public List<Program> displayProgramsInCenterPage(int centerId) {
        Center center = centerRepository.findCenterById(centerId)
                .orElseThrow(() -> new ApiException("Center not found with ID: " + centerId));

        return programRepository.findProgramByCenter(center);
    }

    //Abdulaziz
    //set program status
    public void setProgramStatus(int userId, int programId,String status) {

        //check program if exist
        Program program = programRepository.findProgramById(programId)
                .orElseThrow(() -> new ApiException("Program not found with ID: " + programId));

        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userId));

        //call center
        Center center = centerRepository.findCenterById(user.getCenter().getId())
                .orElseThrow(() -> new ApiException("Center not found with ID: " + user.getCenter().getId()));

        //check the match between center and entered program
        if (center!=program.getCenter()){
            throw new ApiException("Center not match with entered program");
        }
        program.setStatus(status);
        programRepository.save(program);
    }

    //Abdulaziz
    public void deleteProgram(int userId, int programId) {


        //check program if exist
        Program program = programRepository.findProgramById(programId)
                .orElseThrow(() -> new ApiException("Program not found with ID: " + programId));

        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userId));

        //call center
        Center center = centerRepository.findCenterById(user.getCenter().getId())
                .orElseThrow(() -> new ApiException("Center not found with ID: " + user.getCenter().getId()));

        //check the match between center and entered program
        if (center!=program.getCenter()){
            throw new ApiException("Center not match with entered program");
        }

        //check the program state
        if (program.getStatus().equalsIgnoreCase("open")){
            throw new ApiException("Can not delete 'open' program Program is open, Please close the program first");
        }
        programRepository.delete(program);
    }

    //Abdulaziz
    // remove all closed programs
    public void deleteAllClosedPrograms(int userId) {
        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userId));

        Center center = centerRepository.findCenterById(user.getCenter().getId())
                .orElseThrow(() -> new ApiException("Center not found with ID: " + user.getCenter().getId()));

        List<Program> programs = programRepository.findProgramByCenter(user.getCenter());
        for (Program program : programs){
            if (program.getStatus().equalsIgnoreCase("close")){
                programRepository.delete(program);
            }
        }

    }

    //Abdulaziz
    public String displayProgramFinancialReturn(int centerId,int programId) {
        Center center = centerRepository.findCenterById(centerId)
                .orElseThrow(() -> new ApiException("center not found with ID: " + centerId));

        Program program = programRepository.findProgramById(programId)
                .orElseThrow(() -> new ApiException("Program not found with ID: " + programId));

        if (center!=program.getCenter()){
            throw new ApiException("Center not match with entered program");
        }

        return "Total Financial Return of program: "+program.getTitle() +" is: "+program.getProgramFinancialReturn();


    }

    public List<Program> getOpenPrograms(){
        return programRepository.findProgramByStatusOpen("open");
    }

    public List<Program> getProgramByAges(int minAge,int maxAge){
        return programRepository.findProgramByAgeRange(minAge, maxAge).orElseThrow(null);
    }

    //Abdulaziz
    public String displayChildrenNumbers(int centerId, int programId){
        Center center = centerRepository.findCenterById(centerId)
                .orElseThrow(() -> new ApiException("center not found with ID: " + centerId));

        Program program = programRepository.findProgramById(programId)
                .orElseThrow(() -> new ApiException("Program not found with ID: " + programId));

        if (center!=program.getCenter()){
            throw new ApiException("Center not match with entered program");
        }

        return "Number of children's of program: "+program.getTitle() +" is: "+program.getNumOfChildrensInTheProgram();

    }

    //Abdulaziz
    public List<Program> displayProgramsByDateRange(LocalDate startDate, LocalDate endDate){
        return programRepository.findAllProgramsByDateBetween(startDate, endDate).orElseThrow(() -> new ApiException("Not found competitions between this date"));
    }

    //Abdulaziz
    public void expandProgram(int userID,int programId, LocalDate endDate){
        User user = authRepository.findUserById(userID)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userID));

        Program program = programRepository.findProgramById(programId)
                .orElseThrow(() -> new ApiException("Program not found with ID: " + programId));

        if (program.getCenter()!=user.getCenter()){
            throw new ApiException("Center not match with entered program");
        }

        program.setEndDate(endDate);
        programRepository.save(program);
    }

    public List<Child> displayChildrenInTheProgram(int userId, int programId){
        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found with id:" + userId));

        Center center = centerRepository.findCenterById(user.getCenter().getId())
                .orElseThrow(() -> new ApiException("Center not found with id:" + user.getCenter().getId()));

        Program program = programRepository.findProgramById(programId)
                .orElseThrow(() -> new ApiException("Program not found with id:" + programId));

        if (!center.getProgram().contains(program)){
            throw new ApiException("Center not match with the entered program!");
        }

        return programRepository.findAllByChild(program)
                .orElseThrow(() -> new ApiException("child not found with id:" + programId));
    }
}
