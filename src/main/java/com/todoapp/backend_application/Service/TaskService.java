package com.todoapp.backend_application.Service;

import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.DTO.TaskDto;
import com.todoapp.backend_application.ExceptionHandling.TaskNotFoundException;
import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
import com.todoapp.backend_application.Entity.TaskTable;
import com.todoapp.backend_application.Repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    APIResponse apiResponse;

    Logger log = LoggerFactory.getLogger(TaskService.class);
    //This method is used for add the task to the task repository
    public APIResponse addTask(TaskTable taskTable) {
        try {
            taskRepository.save(taskTable);
            apiResponse.setMessage("Task Added Successfully");
            apiResponse.setData(taskTable);
            apiResponse.setStatus(null);
            log.info("Task Added Successfully...........");
            return apiResponse;
        }catch (Exception e){
            throw new TaskNotFoundException("Cannot be add the Task with ID " + taskTable.getTaskId());
        }
    }

    //This method is used for get all the task for particular user by user id
    public APIResponse getAll(long userId) {
        try {
            List<TaskTable> AllTask = taskRepository.findByUserTableUserId(userId);
            apiResponse.setMessage("Got All Tasks Successfully");
            apiResponse.setData(AllTask);
            apiResponse.setStatus(null);
            log.info("Got All Tasks Successfully...........");
            return apiResponse;
        }catch (Exception e){
            throw new TaskNotFoundException("Tasks with User ID " + userId + " not found , Failed to get all task :(");
        }
    }
    //This method is used for get a particular task for particular user
    public APIResponse getById(long id) {
        try {
            Optional<TaskTable> optionalTaskTable = taskRepository.findById(id);
            if(optionalTaskTable.isPresent()) {
                apiResponse.setMessage("Success");
                apiResponse.setData(optionalTaskTable);
                apiResponse.setStatus(null);
                log.info("Got Task by id Successfully...........");
                return apiResponse;
            }else {
                throw new TaskNotFoundException("Task with ID " + id + " not found");
            }
        }catch (Exception e){
            throw new TaskNotFoundException("Task with ID " + id + " not found");
        }
    }
    //This method is used for delete the task by task id
    public APIResponse deleteTaskById(long id) {
        try {
            taskRepository.deleteById(id);
            apiResponse.setMessage("Success");
            apiResponse.setData("Task with ID : " + id + " Deleted Sucessfully");
            apiResponse.setStatus(null);
            log.info("Task with ID : " + id + " Deleted Sucessfully...........");
            return apiResponse;
        }catch (Exception e){
            throw new TaskNotFoundException("Task with ID : " + id+ " not found");
        }
    }
    //This method is used for update existing task by task id from task dto
    public APIResponse updateTaskById(TaskDto taskDto) {
        try {
            Optional<TaskTable> OptionaltaskTable = taskRepository.findById(taskDto.getTaskId());

            if(OptionaltaskTable.isPresent()) {
                TaskTable updatedTaskTable = OptionaltaskTable.get();
                updatedTaskTable.setTaskName(taskDto.getTaskName());
                updatedTaskTable.setDescription(taskDto.getDescription());
                updatedTaskTable.setStatus(taskDto.isStatus());
                taskRepository.save(updatedTaskTable);

                apiResponse.setMessage("Success");
                apiResponse.setData(updatedTaskTable);
                apiResponse.setStatus(null);
                log.info("Task with ID : " + taskDto.getTaskId()+ " Updated Sucessfully...........");
                return apiResponse;
            }else {
                throw new TaskNotFoundException("Task with ID " + taskDto.getTaskId() + " not found");
            }
        }catch (Exception e){
            throw new TaskNotFoundException("Error occurd while fetching Task detail");
        }
    }
    // this method used for sort the tasks of the user by the date
    public APIResponse sortBydate(long userId) {
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "createdDate"); // Sort by date in descending order
            List<TaskTable> taskTables = taskRepository.findByUserTableUserId(userId, sort); // Fetch tasks for the specified userId and sort by createdDate
            apiResponse.setMessage("Success");
            apiResponse.setData(taskTables);
            apiResponse.setStatus(null);
            log.info("Tasks with User ID : " + userId+ " Retrieved Sucessfully...........");
            return apiResponse;
        }
        catch (Exception e){
            throw new UserNotFoundException("Error occurd while fetching Task detail in sorted order");
        }
    }

}
