package com.ngocnb20.travel.model.dto.resp;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRespDto {

    private Long id;
    private int numberView;
    private int numberComment;
    private int numberLike;
    private LocalDate date;
    private String image;
    private String title;
    private String detailSummary;
    private String detail;
    private boolean statusBlog;

    protected LocalDateTime createDate;
    protected LocalDateTime updateTime;
    protected Long createById;
    protected Long lastModifiedBy;
}
