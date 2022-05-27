package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "posts")
public class Post extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_view")
    private int numberView;
    @Column(name = "number_comment")
    private int numberComment;
    @Column(name = "number_like")
    private int numberLike;
    @Column(name = "date_blog")
    private LocalDate date;
    private String image;
    @Column(columnDefinition = "nvarchar(255)")
    private String title;
    @Column(columnDefinition = "longtext")
    private String detail;

    @Column(name = "datail_summary",columnDefinition = "longtext")
    private String detailSummary;


}
