package com.v1.smartbill.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user")
            ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private List<Role> roles = new ArrayList<>();
}