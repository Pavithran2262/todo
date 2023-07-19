package com.todoapp.backend_application.Task_Repository;

import com.todoapp.backend_application.Task_Entity.TaskTable;
import org.springframework.data.jpa.repository.JpaRepository;

 import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskTable, Long> {


    TaskTable getByid(long id);
}
