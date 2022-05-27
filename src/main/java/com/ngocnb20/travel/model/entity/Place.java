package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "places")
public class Place extends Auditable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(columnDefinition = "nvarchar(255)")
        private String name;
        @Column(columnDefinition = "nvarchar(255)")
        private String address;
        @Column(columnDefinition = "nvarchar(255)")
        private String price;
        @Column(name = "number_view")
        private int numberView;
        @Column(name = "number_like")
        private int numberLike;
        @Column(name = "number_comment")
        private int numberComment;
        @Column(columnDefinition = "longtext")
        private String detail;
    //    private String image;

        @OneToMany(mappedBy = "place",cascade = CascadeType.ALL)
        //@JsonManagedReference(value = "imagePlaces")
        private Set<ImagePlace> imagePlaces;

        @OneToMany(mappedBy = "place")
       // @JsonManagedReference(value="categories")
        private Set<CategoryPlace> categories=new HashSet<>();

        @ManyToOne()
        @JoinColumn(name = "author_id",referencedColumnName = "id")

        @JsonIgnore
        private Member member;

}
