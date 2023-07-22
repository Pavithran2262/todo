package com.todoapp.backend_application.Controller;


import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.DTO.TaskDto;
import com.todoapp.backend_application.Entity.TaskTable;
import com.todoapp.backend_application.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/todo/task")
public class TaskDetailController {

    @Autowired
    TaskService taskService;
    @GetMapping(value = "/getall/{user_id}")
    public APIResponse getAllTask(@PathVariable("user_id") long userId){
        return taskService.getAll(userId);
    }
    @GetMapping(value = "/get/{id}")
    public APIResponse getTasKById(@PathVariable("id") long id){
        return taskService.getById(id);
    }
    @PostMapping(value = "/create")
    public APIResponse createTask(@RequestBody TaskTable taskTable){
        return taskService.addTask(taskTable);
    }
    @DeleteMapping(value = "/delete/{id}")
    public APIResponse deleteTaskById(@PathVariable("id")long id){
        return taskService.deleteTaskById(id);
    }
    @PutMapping(value = "/update")
    public APIResponse updateTaskById(@RequestBody TaskDto taskDto){
        return taskService.updateTaskById(taskDto);
    }
    @GetMapping(value = "/sortedtask/{user_id}")
    public APIResponse sortByDate(@PathVariable("user_id") long userId) {
        return taskService.sortBydate(userId);
    }

}
