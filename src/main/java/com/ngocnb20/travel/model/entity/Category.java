package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Table(name = "categorys")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<CategoryPlace> places=new HashSet<>();
    //@JsonManagedReference(value = "places")


}
//    @ManyToMany()
//    @JoinTable(name = "category_place", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "place_id"))