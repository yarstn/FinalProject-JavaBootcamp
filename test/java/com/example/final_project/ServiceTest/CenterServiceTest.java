package com.example.final_project.ServiceTest;

import com.example.final_project.API.ApiException;
import com.example.final_project.DTO.CenterDTO;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Notification;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.NotificationRepository;
import com.example.final_project.Service.CenterService;
import com.example.final_project.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CenterServiceTest {

    @InjectMocks
    CenterService centerService;

    @InjectMocks
    NotificationService notificationService;
    @Mock
    CenterRepository centerRepository;

    @Mock
    AuthRepository authRepository;

    @Mock
    NotificationRepository notificationRepository;

    User user, user1, user2, admin;

    Center center1, center2, center3;

    Notification notification;

    CenterDTO centerDTO;
    List<Center> centers;

    @BeforeEach
    void setUp() {
        user = new User(3, "horseFirst", "FirstHorse@gmail.com",
                "Asdf12345", "0552636692", "First horse", "CENTER"
                , false, null, null, null
                , null, null, null);

        user1 = new User(4, "EnglishAcademy", "EnglishAcademy@gmail.com",
                "Asdf12345", "0554666691", "English Academy", "CENTER"
                , false, null, null, null
                , null, center2, null);

        user2 = new User(5, "FootballAcademy", "FootballAcademy@gmail.com",
                "Asdf12345", "0554783691", "Football Academy", "CENTER"
                , false, null, null, null
                , null, center3, null);

        center1 = new Center(3, "jeddah", "horse Activity center able to learn your child about the horses world"
                , "29384756", Center.Status.IN_PROGRESS, "Sport", 0
                , 0, 0.0, user, null, null, null, null);

        center2 = new Center(4, "Riyadh", "English academy center provide newest technologies to improve child learn",
                "37468263", Center.Status.APPROVED, "Cultural", 7000
                , 15, 4.3, user1, null, null, null, null);

        center3 = new Center(5, "Riyadh", "football academy ensure the build the future of your kid in football",
                "64537928", Center.Status.APPROVED, "Sport", 7000
                , 15, 4.3, user2, null, null, null, null);

        centerDTO = new CenterDTO(3, "EnglishAcademy", "EnglishAcademy@gmail.com", "Asdf12345",
                "English Academy", "CENTER", "0554666991", "jeddah",
                "English academy center provide newest technologies to improve child learn",
                "37468263", null, "Academy");

        admin = new User(1, "Admin--", "Admin@gmail.com",
                "Asdf12345", "0500000000", "Admin", "ADMIN"
                , false, null, null, null
                , null, null, null);

        notification = new Notification(1, Notification.NotificationType.ADMIN_TO_CENTER, "Your center registration has been approved",
                true, null, admin, admin, user2);


    }


    @Test
    public void centerRegisterTest() {
        centerService.centerRegister(centerDTO);
    }

    @Test
    public void centerUpdateTest() {
        user.setCenter(center1);
        authRepository.save(user);
        when(centerRepository.searchCenterById(center1.getId())).thenReturn(center1);
        when(authRepository.searchUserById(user.getId())).thenReturn(user);
        centerService.updateCenter(user.getId(), centerDTO);

        verify(authRepository, times(1)).searchUserById(center1.getId());
    }


    @Test
    public void changePasswordTest() {
        centerRepository.save(center2);
        when(centerRepository.findCenterById(center2.getId())).thenReturn(Optional.of(center2));

        centerService.changePassword(center2.getId(), "Asdf12345", "12345678");

        verify(centerRepository, times(1)).findCenterById(center2.getId());
        verify(authRepository, times(1)).save(center2.getUser());
    }

    @Test
    public void displayCenterFinancialReturnsTest() {
        centerRepository.save(center2);
        when(centerRepository.findCenterById(center2.getId())).thenReturn(Optional.of(center2));
        centerService.displayCenterFinancialReturns(center2.getId());
        verify(centerRepository, times(1)).findCenterById(center2.getId());
    }
}

//    @Test
//    public void approveCenterRegistrationTest(){
//        notificationRepository.save(notification);
//        centerRepository.save(center2);
//        authRepository.save(admin);
//        when(centerRepository.findCenterById(center2.getId())).thenReturn(Optional.of(center2));
//        when(authRepository.findUserById(admin.getId())).thenReturn(Optional.of(admin));
//
//        centerService.approveCenterRegistration(center2.getId());
//
//
//        verify(centerRepository,times(1)).findCenterById(center2.getId());
//        verify(authRepository,times(1)).findUserById(admin.getId());
//        verify(notificationRepository,times(1)).save(notification);
//    }


//    @Test
//    public void rejectCenterRegistrationTest(){
//        notificationRepository.save(notification);
//        centerRepository.save(center2);
//        authRepository.save(admin);
//        when(centerRepository.findCenterById(center2.getId())).thenReturn(Optional.of(center2));
//        when(authRepository.findUserById(admin.getId())).thenReturn(Optional.of(admin));
//
//        centerService.rejectCenterRegistration(center2.getId(),"licence not valid");
//
//
//        verify(centerRepository,times(1)).findCenterById(center2.getId());
//        verify(authRepository,times(1)).findUserById(admin.getId());
//        verify(notificationRepository,times(1)).save(notification);
//    }
//}
