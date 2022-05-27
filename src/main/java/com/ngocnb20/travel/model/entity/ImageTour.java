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
@Table(name = "image_tours")
public class ImageTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="tour_id",referencedColumnName = "id")
    //@JsonBackReference(value = "imageTours")
    private Tour tour =new Tour();
    public ImageTour(String url, Tour tour) {
        this.url = url;
        this.tour = tour;
    }
}
