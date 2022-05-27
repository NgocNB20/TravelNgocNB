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

@Table(name = "image_restaurants")
public class ImageRestaurant {

    public ImageRestaurant(String url, Restaurant restaurant) {
        this.url = url;
        this.restaurant = restaurant;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="id_restaurant",referencedColumnName = "id")
    //@JsonBackReference(value = "imagePlaces")
    private Restaurant restaurant;
}
