package com.ngocnb20.travel.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_category", "id_place"})}
)
public class CategoryPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "id_category",referencedColumnName = "id")
    private  Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_place",referencedColumnName = "id")
    private Place place;


}







//    @EmbeddedId
//    CategoryPlaceKey id;
//
//    @JsonIgnore
//    @ManyToOne()
//    @MapsId("categoryId")
//    @JoinColumn(name = "id_category",referencedColumnName = "id")
//    //@JsonBackReference(value = "places")
//    private Category category;
//
//    @JsonIgnore
//    @ManyToOne()
//    @MapsId("placeId")
//    @JoinColumn(name = "id_place",referencedColumnName = "id")
//    //@JsonBackReference(value = "categories")
//    private Place place;
//    @Column(columnDefinition = "nvarchar(255)")
//    private String name;

//public class CategoryPlace {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long idCategoryPlace;
//    @ManyToOne()
//    @JoinColumn(name = "id_category",referencedColumnName = "id")
//    private Category category;
//    @ManyToOne()
//    @JoinColumn(name = "id_place",referencedColumnName = "id")
//    private Place place;
//}

//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Data
//public class CategoryPlace {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long idCategoryPlace;
//    @ManyToOne()
//    @JoinColumn(name = "id_category",referencedColumnName = "id")
//    private Category category;
//    @ManyToOne()
//    @JoinColumn(name = "id_place",referencedColumnName = "id")
//    private Place place;
//}
