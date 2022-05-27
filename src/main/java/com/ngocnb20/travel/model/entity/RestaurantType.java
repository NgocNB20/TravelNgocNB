package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "restaurant_types" , uniqueConstraints = {@UniqueConstraint(columnNames = {"id_type", "id_restaurant"})})
public class RestaurantType {   //table trung gian TypeOfRestaurant and     Restaurant

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "id_type",referencedColumnName = "id")
    private  TypeOfRestaurant type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_restaurant",referencedColumnName = "id")
    private Restaurant restaurant;
}
