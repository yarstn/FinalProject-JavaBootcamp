package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.*;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.ChildRepository;
import com.example.final_project.Repository.CompetitionRepository;
import com.example.final_project.Repository.ParentReposotiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    // [Mohammed]
    private final CompetitionRepository competitionRepository;
    private final ChildRepository childRepository;
    private final ParentReposotiry parentReposotiry;
    private final AuthRepository authRepository;
    private final NotificationService notificationService;

    public List<Competition> getAllCompetitions(){
        return competitionRepository.findAll();
    }

    public Competition getCompetitionById(Integer id){
        return competitionRepository.findCompetitionById(id).orElseThrow(() -> new ApiException("Competition not found"));
    }

    public void addCompetition(Competition competition){
        competitionRepository.save(competition);
    }

    public void updateCompetition(Integer id, Competition updateCompetition){
        Competition competition = getCompetitionById(id);
        competition.setName(updateCompetition.getName());
        competition.setStartDate(updateCompetition.getStartDate());
        competition.setEndDate(updateCompetition.getEndDate());
        competition.setParticipant(updateCompetition.getParticipant());
        competition.setType(updateCompetition.getType());
        competitionRepository.save(competition);
    }

    public void deleteCompetition(Integer id){
        Competition competition = competitionRepository.findCompetitionById(id).orElseThrow(() -> new ApiException("Competition not found"));
        competitionRepository.delete(competition);
    }


    // +[End-Point]
    public List<Competition> searchCompetitionsByDateRange(LocalDate startDate, LocalDate endDate){
        return competitionRepository.findAllByDateBetween(startDate, endDate).orElseThrow(() -> new ApiException("Not found competitions between this date"));
    }

    // +[End-Point]
    public Competition searchCompetitionByName(String name){
        return competitionRepository.findCompetitionByName(name).orElseThrow(() -> new ApiException("Competition not found"));
    }

    // +[End-Point]
    public List<Competition> searchCompetitionsByType(String type){
        return competitionRepository.findAllByType(type).orElseThrow(() -> new ApiException("Not found competitions by this type"));
    }

    // +[End-Point]
    public List<Competition> searchCompetitionsByAgeRange(Integer ageFrom, Integer ageTo){
        return competitionRepository.findCompetitionsByAgeRange(ageFrom, ageTo).orElseThrow(() -> new ApiException("Not found competitions by this age range"));
    }

    // +[End-Point]
    public List<Competition> getAllCompetitionsForChild(Integer childId){
        Child child = childRepository.findChildById(childId).orElseThrow(() -> new ApiException("Child not found"));
        return competitionRepository.findAllByParticipants(child).orElse(null);
    }


    // +[End-Point]
    public void participationRequest(Integer authId, Integer competitionId, Integer childId) {
        Competition competition = getCompetitionById(competitionId);
        Parent parent = parentReposotiry.findParentById(authId).orElseThrow(() -> new ApiException("Parent not found"));
        Child child = childRepository.findChildById(childId).orElseThrow(() -> new ApiException("Child not found"));
        if(!parent.getChildren().contains(child)){
            throw new ApiException("Child not found");
        }

        if(child.getAge() < competition.getMinAge() || child.getAge() > competition.getMaxAge()){
            throw new ApiException("Child age out of range");
        }

        if (competition.getParticipants().contains(child)) {
            throw new ApiException("Child is already registered for this competition.");
        }

        User admin = authRepository.findUserById(1).orElseThrow(() -> new ApiException("Admin not found"));

        notificationService.createNotification(
                parent.getUser(),
                admin,
                "Parent request to participation his child " + child.getName() + " with ID: (" + child.getId() + ") in " + competition.getName() + " competition",
                Notification.NotificationType.REQUEST_PARTICIPATION
        );
    }

    // +[End-Point]
    public void approvedParticipationRequest(Integer competitionId, Integer childId, Integer parentId) {
        Competition competition = getCompetitionById(competitionId);
        Parent parent = parentReposotiry.findParentById(parentId).orElseThrow(() -> new ApiException("Parent not found"));
        Child child = childRepository.findChildById(childId).orElseThrow(() -> new ApiException("Child not found"));
        User admin = authRepository.findUserById(1).orElseThrow(() -> new ApiException("Admin not found"));

        competition.getParticipants().add(child);
        competitionRepository.save(competition);

        notificationService.createNotification(
                admin,
                parent.getUser(),
                "Congratulation,  your child, " + child.getName() + ", has been approved for the " + competition.getName() + " competition.",
                Notification.NotificationType.ADMIN_TO_PARENT
        );
    }


    // +[End-Point]
    public void rejectParticipationRequest(Integer competitionId, Integer childId, Integer parentId) {
        Competition competition = getCompetitionById(competitionId);
        Parent parent = parentReposotiry.findParentById(parentId).orElseThrow(() -> new ApiException("Parent not found"));
        Child child = childRepository.findChildById(childId).orElseThrow(() -> new ApiException("Child not found"));
        User admin = authRepository.findUserById(1).orElseThrow(() -> new ApiException("Admin not found"));

        notificationService.createNotification(
                admin,
                parent.getUser(),
                "Unfortunately, your child, " + child.getName() + ", has not been approved for the " + competition.getName() + " competition.",
                Notification.NotificationType.ADMIN_TO_PARENT
        );
    }

    // +[End-Point]
    public void cancelChildParticipation(Integer parentId, Integer childId, Integer competitionId) {
        Competition competition = getCompetitionById(competitionId);
        Parent parent = parentReposotiry.findParentById(parentId).orElseThrow(() -> new ApiException("Parent not found"));
        Child child = childRepository.findChildById(childId).orElseThrow(() -> new ApiException("Child not found"));

        if(!parent.getChildren().contains(child)){
            throw new ApiException("Sorry you don't have permission to access on this child");
        }

        competition.getParticipants().remove(child);
        competitionRepository.save(competition);
    }
}


