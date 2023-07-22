package com.todoapp.backend_application.API_Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    private Object status;
    private Object data;
    private String message;

}
