package com.ngocnb20.travel.model.dto.resp;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRespDto<T> {
    private int totalPage;
    private List<T> pageRespDtos;
    private Long totalItem;

    public PageRespDto(int totalPage, List<T> pageRespDtos) {
        this.totalPage = totalPage;
        this.pageRespDtos = pageRespDtos;
    }

}
