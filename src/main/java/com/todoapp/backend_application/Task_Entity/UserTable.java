package com.todoapp.backend_application.Task_Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @NonNull
    @Column(name="username")
    private String userName;

    @NonNull
    @Column(name="password")
    private String password;
}
