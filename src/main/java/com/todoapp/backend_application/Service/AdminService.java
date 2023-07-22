package com.todoapp.backend_application.Service;

import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.DTO.AuthRequest;
import com.todoapp.backend_application.Entity.UserTable;
import com.todoapp.backend_application.ExceptionHandling.UserAlreadyExistException;
import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
import com.todoapp.backend_application.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    APIResponse apiResponse;
    @Autowired
    PasswordEncoder passwordEncoder;
    private static final String admin_role = "ADMIN";

    Logger log = LoggerFactory.getLogger(AdminService.class);

    //This registerAdmin() is used to register the admin into database via service class
    public APIResponse registerAdmin(AuthRequest authRequest) throws Exception {
        Optional<UserTable> optionalAdminTable;
        try {
//            log.debug("\n \n      " + authRequest.getName() + "    \n \n ");
            optionalAdminTable = userRepository.findByUsername(authRequest.getName());
            log.info("\n \n     " + optionalAdminTable + "   \n \n ");
            log.debug(" optionalUserTable.isEmpty() ------------" + optionalAdminTable.isEmpty() + "    ==============");
        } catch (Exception e) {
            throw new Exception("Cannot be add the Admin with the Name " + authRequest.getName());
        }
        if (optionalAdminTable.isEmpty()) {
            UserTable userTable = new UserTable();
            userTable.setUserName(authRequest.getName());
            log.info("inside register admin method ...............");
            userTable.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            userTable.setRole(admin_role);
            userRepository.save(userTable);
            apiResponse.setMessage("Admin Added Successfully");
            apiResponse.setData(userTable);
            apiResponse.setStatus(null);
            log.info("Admin Added Successfully...........");
            return apiResponse;
        } else {
            throw new UserAlreadyExistException("Admin name" + authRequest.getName() + " Already Exist ");
        }
    }

    //This method used to change the password of the admin by accessing repository
    public APIResponse changeAdminPassword(AuthRequest authRequest) {
        try {
            Optional<UserTable> OptionalAdminTable = userRepository.findByUsername(authRequest.getName());

            if (OptionalAdminTable.isPresent()) {
                log.info("Adminname " + OptionalAdminTable.isPresent()+ " ...........");
                UserTable updatedAdminTable = OptionalAdminTable.get();
                updatedAdminTable.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                userRepository.save(updatedAdminTable);

                apiResponse.setMessage("Success");
                apiResponse.setData(updatedAdminTable);
                apiResponse.setStatus(null);
                log.info("Adminname " + authRequest.getName() + " Updated Sucessfully...........");
                return apiResponse;
            } else {
                throw new UserNotFoundException("Admin with Name" + authRequest.getName() + " not found");
            }
        } catch (Exception e) {
            throw new UserNotFoundException("Error occurred while Updating Admin detail");
        }
    }
//this method is used to get the all user that is only accessible to admin
    public APIResponse getAllUsers() {
        try {
            log.debug(" from get user service .............");
            log.debug(" from get user service ........."+userRepository.findAllUsers()+"....");
            List<UserTable> userTableList = userRepository.findAllUsers();
            log.debug(" from get user service==== .............");
            apiResponse.setStatus("Successfully retrieved all users");
            apiResponse.setData(userTableList);
            apiResponse.setStatus(null);
            return apiResponse;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error occurred while Retrieve all users detail");
        }
    }
}
