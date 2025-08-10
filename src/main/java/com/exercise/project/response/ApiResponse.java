package com.exercise.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {

    private String message;

    private Boolean success;

    private Object data;

}
