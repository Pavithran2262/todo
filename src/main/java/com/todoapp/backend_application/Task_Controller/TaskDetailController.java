package com.todoapp.backend_application.Task_Controller;


import com.todoapp.backend_application.Task_Entity.TaskTable;
import com.todoapp.backend_application.Task_Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/task")
public class TaskDetailController {

    @Autowired
    TaskService taskService;
    @GetMapping(value = "/getall")
    public List<TaskTable> getAllTask(){
        return taskService.getAll();
    }
    @GetMapping(value = "/get/{id}")
    public TaskTable getTasKById(@PathVariable("id") long id){
        return taskService.getById(id);
    }
    @PostMapping(value = "/create")
    public TaskTable createTask(@RequestBody TaskTable taskTable){
        return taskService.addTask(taskTable);
    }
    @DeleteMapping

}
