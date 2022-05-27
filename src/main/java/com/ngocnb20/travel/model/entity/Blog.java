package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "blogs")
public class Blog extends Auditable {


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
    @ManyToOne()
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    @JsonIgnore
    private Member member;
    public boolean statusBlog;


    public Blog(Long id, int numberView, int numberComment, int numberLike, LocalDate date, String image, String title, String detail, String detailSummary, Member member) {
        this.id = id;
        this.numberView = numberView;
        this.numberComment = numberComment;
        this.numberLike = numberLike;
        this.date = date;
        this.image = image;
        this.title = title;
        this.detail = detail;
        this.detailSummary = detailSummary;
        this.member = member;
    }




}
