package com.example.shop.repository;

import com.example.shop.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends Repository<User, Long> {
    Optional<User> findUserByUsername(String username);

    User save(User user);

    @Query
    Optional<User> findUserById(@Param("userId") Long userId);

    boolean existsUserById(@Param("userId") Long userId);


}
