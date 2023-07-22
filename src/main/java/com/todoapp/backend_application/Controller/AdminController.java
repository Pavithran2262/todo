package com.todoapp.backend_application.Controller;

import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.DTO.AuthRequest;
import com.todoapp.backend_application.ExceptionHandling.GlobalException;
import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
import com.todoapp.backend_application.Service.AdminService;
import com.todoapp.backend_application.Service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    AdminService adminService;
    @Autowired
    APIResponse apiResponse;

    Logger log = LoggerFactory.getLogger(GlobalException.class);

    //This registerAdmin() is used to register the admin into database via service class
    @PostMapping("/register")
    public APIResponse register(@RequestBody AuthRequest authRequest) throws Exception {
        return adminService.registerAdmin(authRequest);
    }

    //This method used to change the password of the admin by accessing repository via adminservice class
    @PutMapping("/update")
    public APIResponse changeAdminPassword(@RequestBody AuthRequest authRequest){
        return adminService.changeAdminPassword(authRequest);
    }

    @PostMapping("/login")
    public APIResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
            log.info("................From login controller ........................");
            String token = jwtService.generateToken(authRequest.getName(),auth );
            System.out.println(token);
            apiResponse.setStatus("Success");
            apiResponse.setData(token);
            return apiResponse;
        } catch (Exception ex) {
            throw new UserNotFoundException("Access denied for given Credentials");
        }
    }
    @GetMapping("/getalluser")
    public APIResponse getAllUser(){
        return adminService.getAllUsers();
    }


}
