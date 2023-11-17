package com.example.mercadonarestapi.repository;




import com.example.mercadonarestapi.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
