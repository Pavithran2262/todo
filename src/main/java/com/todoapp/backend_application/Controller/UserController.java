package com.todoapp.backend_application.Controller;

import com.todoapp.backend_application.API_Response.APIResponse;
import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
import com.todoapp.backend_application.DTO.AuthRequest;
import com.todoapp.backend_application.Service.JwtService;
import com.todoapp.backend_application.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor//for test case purpose
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

//Register the new user to user table
    @PostMapping(value = "/register")
    public APIResponse createUser(@RequestBody AuthRequest authRequest) throws Exception {
        return userService.addUser(authRequest);
    }
    @GetMapping(value = "/welcome")
    public APIResponse welcome (){
        return userService.welcome();
    }


    @PutMapping("/update")
    public APIResponse changeUserPassword(@RequestBody AuthRequest authRequest){
        return userService.changeUserPassword(authRequest);
    }

    @Autowired
    APIResponse apiResponse;
    @PostMapping("/login")
//    @PreAuthorize("USER")
    public APIResponse generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        String token ;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword())
            );
            token = jwtService.generateToken(authRequest.getName(),auth);
        } catch (Exception ex) {
            throw new UserNotFoundException("Access denied for given Credentials");
        }
        apiResponse.setStatus(null);
        apiResponse.setData(token);
        apiResponse.setMessage("Success");
        return apiResponse;
    }

}
