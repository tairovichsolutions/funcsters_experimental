package io.funcsters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Judge0SubmissionRequest {
    @JsonProperty("source_code")
    private String sourceCode;
    @JsonProperty("language_id")
    private Integer languageId;
    @JsonProperty("stdin")
    private String stdin;
}