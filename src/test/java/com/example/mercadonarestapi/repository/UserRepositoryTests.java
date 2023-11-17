package com.example.mercadonarestapi.repository;


import com.example.mercadonarestapi.pojo.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_Save_ReturnSavedUser() {
        User user = User.builder().firstname("Bernard").lastname("Lemoine").email("bl@example.com").password("QWEFJKLCUH23N").build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getFirstname()).isEqualTo("Bernard");
    }

    @Test
    public void UserRepository_FindByEmail_ReturnUser() {
        User user = User.builder().firstname("Bernard").lastname("Lemoine").email("bl@example.com").password("QWEFJKLCUH23N").build();
        User savedUser = userRepository.save(user);

        Optional<User> userFound = userRepository.findByEmail(savedUser.getEmail());

        Assertions.assertThat(userFound).isNotNull();
        Assertions.assertThat(userFound.get().getPassword()).isEqualTo("QWEFJKLCUH23N");
    }
}
