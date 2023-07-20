package com.todoapp.backend_application.Task_Service;

import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.DTO.UserDto;
import com.todoapp.backend_application.ExceptionHandling.TaskNotFoundException;
import com.todoapp.backend_application.ExceptionHandling.UserAlreadyExistException;
import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
import com.todoapp.backend_application.Task_Entity.TaskTable;
import com.todoapp.backend_application.Task_Entity.UserTable;
import com.todoapp.backend_application.Task_Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    APIResponse apiResponse;


    Logger log = LoggerFactory.getLogger(TaskService.class);

    public APIResponse changeUsernameAndPassword(UserDto userDto) {
        try {
            Optional<UserTable> OptionaltaskTable = userRepository.findById(userDto.getUserId());

            if(OptionaltaskTable.isPresent()) {
                UserTable updatedUserTable = OptionaltaskTable.get();
                updatedUserTable.setUserName(userDto.getUserName());
                updatedUserTable.setPassword(userDto.getPassword());
                userRepository.save(updatedUserTable);

                apiResponse.setMessage("Success");
                apiResponse.setData(updatedUserTable);
                apiResponse.setStatus(null);
                log.info("User with ID : " + userDto.getUserId()+ " Updated Sucessfully...........");
                return apiResponse;
            }else {

                throw new UserNotFoundException("User with ID " + userDto.getUserId() + " not found");
            }
        }catch (Exception e){
            throw new UserNotFoundException("Error occurd while Updating User detail");
        }
    }

    public APIResponse addUser(UserDto userDto) throws Exception {
        try {
            UserTable optionalUserTable = userRepository.findByuserName(userDto.getUserName());
            if(optionalUserTable.getUserName().isEmpty()){
                UserTable userTable = new UserTable();
                userTable.setUserName(userDto.getUserName());
                userTable.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRepository.save(userTable);
                apiResponse.setMessage("User Added Successfully");
                apiResponse.setData(userTable);
                apiResponse.setStatus(null);
                log.info("User Added Successfully...........");
                return apiResponse;
            }else {
                throw new UserAlreadyExistException("Username"+userDto.getUserName()+" Already Exist ");
            }

        }catch (Exception e){
            throw new Exception("Cannot be add the User with ID " + userDto.getUserId()+" And Name "+userDto.getUserName());
        }
    }

}
