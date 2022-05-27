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
@Table(name = "restaurant_foods" , uniqueConstraints = {@UniqueConstraint(columnNames = {"id_food", "id_restaurant"})})
public class RestaurantFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "id_food",referencedColumnName = "id")
    private  Food food;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_restaurant",referencedColumnName = "id")
    private Restaurant restaurant;

}
