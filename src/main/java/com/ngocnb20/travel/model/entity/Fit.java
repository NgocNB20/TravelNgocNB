package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "fit_restaurants")
public class Fit {
    @Id
    private Long id;
    @Column(columnDefinition = "nvarchar(50)" , name="fit_name")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "fit")
    private Set<RestaurantFit>  restaurantFits = new HashSet<>();
}
