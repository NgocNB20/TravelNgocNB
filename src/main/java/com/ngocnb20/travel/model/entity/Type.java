//package com.ngocnb20.travel.model.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//
//@Table(name = "types")
//public class Type {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    @OneToOne(mappedBy = "type")
//    private Blog blog;
//    @OneToOne(mappedBy = "tour")
//    private Tour tour;
//    @OneToOne
//    private Place place;
//    @OneToOne
//    private Hotel hotel;
//
//
//
//
//}
