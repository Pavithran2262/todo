package com.todoapp.backend_application.Repository;

import com.todoapp.backend_application.Entity.TaskTable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

 import org.springframework.stereotype.Repository;

import java.util.List;

//this repository used for store and retrive task related details
@Repository
public interface TaskRepository extends JpaRepository<TaskTable, Long> {

    //this method used to find the task by user id from the user table
    List<TaskTable> findByUserTableUserId(long userId);
    //this method used to find the task with sort by user id from the user table
    List<TaskTable> findByUserTableUserId(long userId, Sort sort);


}
