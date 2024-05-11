package com.voda.eip.model;

import com.voda.eip.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String username;

    @Size(max = 40)
    @Email
    private String email;

    @Size(max = 400)
    private String address;

    @Size(max = 40)
    private String phone;

    @NotBlank
    @Size(max = 100)
    private String password;

    @Column(name = "serial_weigher")
    @Size(max = 40)
    private String serialWeigher;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String name, String username, String email, String password, String serialWeigher) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.serialWeigher = serialWeigher;
    }

}
