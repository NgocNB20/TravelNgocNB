package com.ngocnb20.travel.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailParamDto {
    private String subject; //title tin gửi
    private String[] emailTo;
    private String[] ccList;  //gửi kèm bìa phụ
    private String[] bccList; //ẩn danh
    private String content;
    private boolean IsHtml=true;

}
