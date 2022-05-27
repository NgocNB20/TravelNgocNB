package com.ngocnb20.travel.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//data back-end trả về với api
public class BaseRespDto   {

    private boolean success;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static BaseRespDto success(String message, Object data) {
        return BaseRespDto.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static BaseRespDto error(String message, String errorCode) {
        return BaseRespDto.builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .build();
    }

    public static BaseRespDto error(String message, String errorCode, Object data) {
        return BaseRespDto.builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .data(data)
                .build();
    }
    public static BaseRespDto error(String message, Object data) {
        return BaseRespDto.builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }
    public static BaseRespDto error(String message) {
        return BaseRespDto.builder()
                .data("null")
                .success(false)
                .message(message)
                .build();
    }
}
