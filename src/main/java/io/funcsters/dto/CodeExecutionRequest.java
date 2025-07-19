package io.funcsters.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeExecutionRequest {
    @NotBlank(message = "Source code is required")
    private String userCode;
    @NotBlank(message = "Language is required")
    private String language;
}