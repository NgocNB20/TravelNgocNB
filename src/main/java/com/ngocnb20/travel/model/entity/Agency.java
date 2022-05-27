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

@Table(name ="agencies" )
public class Agency extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    private String image;
    @Column(columnDefinition = "nvarchar(255)")
    private String address;
    @Column(name ="type_agency", columnDefinition = "nvarchar(255)")
    private String typeAgency;
    @Email
    private String email;
    @Column(name = "url_web")
    private String urlWeb;
    private String phone;
    @Column(columnDefinition = "text")
    private String detail;
    @Column(name = "number_view")
    private int numberView;
    @Column(name = "total_like")
    private int numberLike;

    @ManyToOne()
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    @JsonIgnore
    private Member member;


}
