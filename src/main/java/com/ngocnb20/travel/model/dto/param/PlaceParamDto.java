package com.ngocnb20.travel.model.dto.param;

import com.ngocnb20.travel.model.entity.CategoryPlace;
import com.ngocnb20.travel.model.entity.ImagePlace;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlaceParamDto implements Serializable {
    private int numberView;
    private int numberComment;
    private int numberLike;
    private String name;
    private String address;
    private String price;
    private LocalDate date;
    private String detail;
    private MultipartFile[] files;
    private Set<ImagePlace> imagePlaces;
    private Set<CategoryPlace> categories = new HashSet<>();








}
