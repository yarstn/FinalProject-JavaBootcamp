package com.example.final_project.RepositoryTest;

import com.example.final_project.Model.Center;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CenterRepositoryTest {

    @Autowired
    CenterRepository centerRepository;

    @Autowired
    AuthRepository authRepository;

    User user, user2;

    Center center1, center2, center3;

    List<Center> centers;

    Center center;

    @BeforeEach
    void setUp(){
        user=new User(null,"horseHouse","horseHouse@gmail.com",
                "12345Asf","0564735245","horse house","CENTER"
                ,false,null,null,null
                ,null,center,null);

        user2=new User(null,"EnglishAcademy","EnglishAcademy@gmail.com",
                "Asdf12345","0554666691","English Academy","CENTER"
                ,false,null,null,null
                ,null,center2,null);

        center1 = new Center(null,"jeddah","horse Activity center able to learn your child about the horses world"
                ,"29384756", Center.Status.IN_PROGRESS,"Sport",0
                ,0,0.0,user,null,null,null,null);


        center2 = new Center(null,"Riyadh","English academy center provide newest technologies to improve child learn",
                "37468263",null,"Cultural",7000
                ,15,4.3,user2,null,null,null,null);
    }

    @Test
    public void findCenterById(){
        authRepository.save(user);
        centerRepository.save(center1);
        center=centerRepository.findCenterById(center1.getId()).orElse((null));
        Assertions.assertThat(center).isEqualTo(center1);
    }
}
