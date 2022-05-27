package com.ngocnb20.travel.model.dto.resp;

import com.ngocnb20.travel.model.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagePlaceDto implements Serializable {

    private Long id;
    private String url;

}
