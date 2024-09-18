package com.example.final_project.RepositoryTest;

import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.ChildRepository;
import com.example.final_project.Repository.ParentReposotiry;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChildRepositoryTest {

    @Autowired
    ChildRepository childRepository;

    @Autowired
    ParentReposotiry parentReposotiry;


    Child child1,child;

    List<Child> childList;

    Parent parent;

    User user;

    ParentDTO parentDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);

        parent = new Parent();
        parent.setId(1);
        parent.setUser(user);

        parentDTO = new ParentDTO();
        parentDTO.setUsername("Kshalid");
        parentDTO.setPassword("49dc404b7733");
        parentDTO.setEmail("testuser@example.com");
        parentDTO.setName("Test User");
        parentDTO.setRole("PARENT");
        parentDTO.setPhoneNumber("1234567890");

        child1 = new Child(null,"Abdullah","null",
                8,null,null,"male",
                parent,null,null,null,null);

        childList = new ArrayList();
        childList.add(child1);
        parent.getChildren().add(child1);
        childRepository.save(child);
        parentReposotiry.save(parent);

    }

    @Test
    public void findChildByIdTest(){

        child = childRepository.findById(child.getId()).orElse(null);
        Assertions.assertThat(child).isEqualTo(child1);
    }

    @Test
    public void findParentByIdTest(){

        childList = childRepository.findAllByParent(parent);
        Assertions.assertThat(childList.get(0).getName()).isEqualTo("Abdullah");

    }
}
