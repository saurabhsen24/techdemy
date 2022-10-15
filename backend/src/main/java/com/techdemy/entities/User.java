package com.techdemy.entities;

import com.techdemy.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('USER','ADMIN') DEFAULT 'USER'", nullable = false)
    private Role role;

    @Override
    public String toString(){
        return "User { userId = " + userId + ", userName = " + userName + ", email = " + email +
                ", password = " + password + " }";
    }

}
