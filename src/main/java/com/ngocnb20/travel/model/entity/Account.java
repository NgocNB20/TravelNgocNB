package com.ngocnb20.travel.model.entity;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    @Nationalized
    @Column(columnDefinition = "nvarchar(255)")
    private String username;
    @Size(min = 8)
    private String password;

    @ManyToOne()
    @JoinColumn(name = "id_role",referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "account")
    Set<Menu> menus=new HashSet<>();

}
