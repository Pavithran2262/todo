package com.todoapp.backend_application.Repository;

import com.todoapp.backend_application.Entity.TaskTable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

 import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskTable, Long> {

    List<TaskTable> findByUserTableUserId(long userId);
    List<TaskTable> findByUserTableUserId(long userId, Sort sort);


}
