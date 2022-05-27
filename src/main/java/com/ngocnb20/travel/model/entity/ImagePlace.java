package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngocnb20.travel.model.entity.Place;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "image_places")
public class ImagePlace {
    public ImagePlace(String url) {
        this.url = url;
    }

    public ImagePlace(String url, Place place) {
        this.url = url;
        this.place = place;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_place",referencedColumnName = "id")
    //@JsonBackReference(value = "imagePlaces")
    private Place place;


}
