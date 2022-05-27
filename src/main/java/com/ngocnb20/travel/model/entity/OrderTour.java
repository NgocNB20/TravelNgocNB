package com.ngocnb20.travel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Order_Tour")
public class OrderTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_customer",columnDefinition = "nvarchar(255)")
    private String nameCustomer;
    private String email;
    private String phone;
    private String note;
    @Column(name = "name_tour",columnDefinition = "longtext")
    private String nameTour;
    @Column(name = "date_order")
    private LocalDateTime dateOrder;
    private boolean status=false;
}
