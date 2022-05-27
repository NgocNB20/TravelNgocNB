package com.ngocnb20.travel.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "Order_Hotel")
public class OrderHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_customer",columnDefinition = "nvarchar(255)")
    private String nameCustomer;
    private String email;
    private String phone;
    @Column(columnDefinition = "longtext")
    private String note;
    @Column(name = "name_hotel",columnDefinition = "nvarchar(255)")
    private String nameHotel;
    @Column(name = "date_order")
    private LocalDateTime dateOrder;
    private Boolean status;
}
