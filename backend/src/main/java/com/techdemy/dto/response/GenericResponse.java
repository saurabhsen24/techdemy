package com.techdemy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse {

    private String message;

    public static GenericResponse buildGenericResponse(String message){
        return GenericResponse.builder()
                .message(message)
                .build();
    }

}
