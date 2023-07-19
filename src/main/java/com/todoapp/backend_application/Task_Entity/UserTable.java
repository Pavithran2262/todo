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
    @OneToMany
    private long userId;
    @NonNull
    private String userName;

    @NonNull
    private String password;
}
