package com.ngocnb20.travel.model.dto.param;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailReceiveDto {
    private String[] email;
    private String content;
    private String subject;
}
