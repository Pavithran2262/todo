package com.todoapp.backend_application.Repository;

import com.todoapp.backend_application.Entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTable,Long> {

    @Query(value = "SELECT * FROM user_table WHERE user_name = ?1", nativeQuery = true)
    Optional<UserTable> findByUsername(String username);
    @Query(value = "SELECT user_name, user_id, role FROM user_table ", nativeQuery = true)
    List<UserTable> findAllUsers();


}

