package com.ngocnb20.travel.model.dto.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogPramDto implements Serializable {
    private int numberView;
    private int numberComment;
    private int numberLike;
    private LocalDate date;
    private String title;
    private String detail;
    private boolean statusBlog;
    private String detailSummary;
    private MultipartFile file;
}
