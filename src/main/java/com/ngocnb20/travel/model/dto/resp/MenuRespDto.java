package com.ngocnb20.travel.model.dto.resp;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuRespDto implements Serializable {
    private String name;
    private String url;


}
