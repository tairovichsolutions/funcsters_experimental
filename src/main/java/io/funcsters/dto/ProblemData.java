package io.funcsters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProblemData {

    @JsonProperty("test_cases")
    private String testCases;

    @JsonProperty("language")
    private Map<String, Language> language;

    @Getter
    @Setter
    public static class Language{
        @JsonProperty("wrapper")
        private String wrapper;
        @JsonProperty("solution")
        private String solution;
        @JsonProperty("language_id")
        private Integer languageId;
    }
}


