//package com.todoapp.backend_application;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.todoapp.backend_application.API_Response.APIResponse;
//import com.todoapp.backend_application.DTO.AuthRequest;
//import com.todoapp.backend_application.DTO.UserDto;
//import com.todoapp.backend_application.ExceptionHandling.UserNotFoundException;
//import com.todoapp.backend_application.Controller.UserController;
//import com.todoapp.backend_application.Service.JwtService;
//import com.todoapp.backend_application.Service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//public class LoginControllerTest {
//
//    private UserController loginController;
//
//    private UserService userService;
//    private JwtService jwtService;
//    private AuthenticationManager authenticationManager;
//
//    @BeforeEach
//    public void setUp() {
//        userService = mock(UserService.class);
//        jwtService = mock(JwtService.class);
//        authenticationManager = mock(AuthenticationManager.class);
//
//        loginController = new UserController(userService, jwtService, authenticationManager);
//    }
//
//    @Test
//    public void testCreateTask() throws Exception {
//        UserDto userDto = new UserDto(/* add necessary userDto parameters */);
//        APIResponse expectedResponse = new APIResponse(/* add necessary APIResponse parameters */);
//
//        when(userService.addUser(userDto)).thenReturn(expectedResponse);
//
//        APIResponse response = loginController.createUser(userDto);
//
//        assertEquals(expectedResponse, response);
//    }
//
//    @Test
//    public void testChangeUsernameAndPassword() {
//        UserDto userDto = new UserDto(/* add necessary userDto parameters */);
//        APIResponse expectedResponse = new APIResponse(/* add necessary APIResponse parameters */);
//
//        when(userService.changeUserPassword(userDto)).thenReturn(expectedResponse);
//
//        APIResponse response = loginController.changeUserPassword(userDto);
//
//        assertEquals(expectedResponse, response);
//    }
//
//    @Test
//    public void testGenerateToken_Success() throws Exception {
//        String username = "testUser";
//        AuthRequest authRequest = new AuthRequest("testUser", "password");
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
//        when(jwtService.generateToken(username)).thenReturn("dummyToken");
//
//        String token = loginController.generateToken(authRequest);
//
//        assertEquals("dummyToken", token);
//    }
//
//    @Test
//    public void testGenerateToken_UserNotFound() throws Exception {
//        AuthRequest authRequest = new AuthRequest("nonExistentUser", "password");
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UserNotFoundException("User not found"));
//
//        assertThrows(UserNotFoundException.class, () -> loginController.generateToken(authRequest));
//    }
//}
//
