package com.ngocnb20.travel.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    private String url;
    private boolean status;
    private String icon;
    @ManyToOne()
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private Account account;
}
