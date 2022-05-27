package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "hotels")
public class Hotel extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    @Column(name = "number_view")
    private int numberView;
    private int rate;
    @Column(name = "number_like")
    private int numberLike;
    @Column(columnDefinition = "nvarchar(255)")
    private String address;
    @Email
    private String email;
    @Column(name = "url_web")
    private String urlWeb;
    private String phone;
    @Column(columnDefinition = "longtext")
    private String detail;
    private double price;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    @JsonIgnore
    private Member member;


}
