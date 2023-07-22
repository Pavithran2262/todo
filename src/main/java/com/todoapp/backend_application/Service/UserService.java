package com.todoapp.backend_application.Service;

import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.DTO.AuthRequest;
import com.todoapp.backend_application.ExceptionHandling.UserAlreadyExistException;
import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
import com.todoapp.backend_application.Entity.UserTable;
import com.todoapp.backend_application.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private static final String user_role = "USER";

    public APIResponse changeUserPassword(AuthRequest authRequest) {
        Optional<UserTable> OptionalUserTable;
        try {
            OptionalUserTable = userRepository.findByUsername(authRequest.getName());
        } catch (Exception e) {
            throw new UserNotFoundException("Error occurred while Updating User detail");
        }
        if (OptionalUserTable.isPresent()) {
            UserTable updatedUserTable = OptionalUserTable.get();
            updatedUserTable.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            userRepository.save(updatedUserTable);

            apiResponse.setMessage("Success");
            apiResponse.setData(updatedUserTable);
            apiResponse.setStatus(null);
            log.info("User with ID : " + authRequest.getName() + " Updated Sucessfully...........");
            return apiResponse;
        } else {
            throw new UserNotFoundException("User with name " + authRequest.getName() + " not found");
        }

    }

    public APIResponse addUser(AuthRequest authRequest) throws Exception {
        Optional<UserTable> optionalUserTable;
        try {
//            log.debug("hi///////////////////////////////////");
            optionalUserTable = userRepository.findByUsername(authRequest.getName());
            log.debug(" optionalUserTable.isEmpty() ------------" + optionalUserTable.isEmpty() + "    ==============");
        } catch (Exception e) {
            throw new Exception("Cannot be add the User with name " + authRequest.getName());
        }
            if (optionalUserTable.isEmpty()) {
                UserTable userTable = new UserTable();
                userTable.setUserName(authRequest.getName());
                userTable.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                userTable.setRole(user_role);
                userRepository.save(userTable);
                apiResponse.setMessage("User Added Successfully");
                apiResponse.setData(userTable);
                apiResponse.setStatus(null);
                log.info("User Added Successfully...........");
                return apiResponse;
            } else {
                throw new UserAlreadyExistException("Username" + authRequest.getName() + " Already Exist ");
            }


    }

    public APIResponse welcome() {
        apiResponse.setMessage("Success");
        apiResponse.setData("Welcome to To-Do-App ...");
        apiResponse.setStatus(null);
        return apiResponse;
    }
}
