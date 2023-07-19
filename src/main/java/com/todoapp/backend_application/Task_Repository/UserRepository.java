package com.todoapp.backend_application.Task_Repository;

import com.todoapp.backend_application.Task_Entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTable,Long> {

}