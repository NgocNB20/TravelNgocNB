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
@Table(name = "restaurants")
public class Restaurant extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @Column(columnDefinition = "nvarchar(255)")
    private String address;
    @Column(name = "number_like")
    private int numberLike;
    @Column(name = "number_view")
    private int numberView;
    @Column(columnDefinition = "longtext")
    private String detail;
    @Column(columnDefinition = "varchar(50)")
    String phone;
    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantFood> restaurantFoods = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantType> restaurantTypes = new HashSet<>();
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private Set<ImageRestaurant> imageRestaurants = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantFit> restaurantFits = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    @JsonIgnore
    private Member member;





}
