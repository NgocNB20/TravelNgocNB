package com.ngocnb20.travel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String email;
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "reset_password")
    private String resetPassword;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<RoleMember> roleMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Hotel> hotels = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Tour> tours = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Restaurant> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Blog> blogs = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Agency> agencies = new ArrayList<>();


    public Member(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
