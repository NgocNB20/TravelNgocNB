//package com.ngocnb20.travel.model.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Embeddable
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//
//public class CategoryPlaceKey implements Serializable {
//
//    @Column(name = "category_id")
//    Long categoryId;
//    @Column(name = "place_id")
//    Long placeId;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CategoryPlaceKey that = (CategoryPlaceKey) o;
//        return Objects.equals(categoryId, that.categoryId) && Objects.equals(placeId, that.placeId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(categoryId, placeId);
//    }
//}
