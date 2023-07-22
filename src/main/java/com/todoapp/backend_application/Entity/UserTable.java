package com.todoapp.backend_application.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userTable")
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private long userId;
    @NonNull
    @Column(name="userName")
    private String userName;

    @NonNull
    @Column(name="password")
    private String password;

    @NonNull
    @Column(name ="role")
    private String role;

}
