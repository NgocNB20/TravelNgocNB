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
@Table(name = "foods")
public class Food {
    @Id
    private Long id;
    @Column(name = "name_food" , columnDefinition = "nvarchar(50)" )
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "food")
    private Set<RestaurantFood> restaurantFoods = new HashSet<>();
}
