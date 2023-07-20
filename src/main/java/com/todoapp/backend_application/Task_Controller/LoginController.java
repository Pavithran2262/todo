package com.todoapp.backend_application.Task_Controller;

import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.DTO.UserDto;
import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
import com.todoapp.backend_application.DTO.AuthRequest;
import com.todoapp.backend_application.Task_Entity.UserTable;
import com.todoapp.backend_application.Task_Service.JwtService;
import com.todoapp.backend_application.Task_Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping(value = "/register")
    public APIResponse createTask(@RequestBody UserDto userDto) throws Exception {
        return userService.addUser(userDto);
    }


    @PutMapping("/update")
    public APIResponse changeUsernameAndPassword(@RequestBody UserDto userDto){
        return userService.changeUsernameAndPassword(userDto);
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new UserNotFoundException("Access denied for given Credentials");
        }
        return jwtService.generateToken(authRequest.getUserName());
    }

}
