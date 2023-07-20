package com.todoapp.backend_application.Task_Repository;

import com.todoapp.backend_application.Task_Entity.TaskTable;
import com.todoapp.backend_application.Task_Entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTable,Long> {


    UserTable findByuserName(String username);

}