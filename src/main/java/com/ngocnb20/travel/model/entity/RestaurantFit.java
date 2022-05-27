package com.ngocnb20.travel.model.entity;

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
@Table(name = "restaurant_fits" , uniqueConstraints = {@UniqueConstraint(columnNames = {"id_restaurant" , "id_fit"})})
public class RestaurantFit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne()
    @JoinColumn(name = "id_restaurant" , referencedColumnName = "id")
    private Restaurant restaurant;
    @ManyToOne()
    @JoinColumn(name = "id_fit" , referencedColumnName = "id")
    private Fit fit;
}
