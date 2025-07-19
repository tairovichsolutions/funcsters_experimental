package io.funcsters.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Judge0Response {
    private String stdout;
    private String time;
    private int memory;
    private String stderr;
    private String token;
    private String compile_output;
    private String message;
    private Status status;

    // Getters and setters

    @Getter
    @Setter
    public static class Status {
        private int id;
        private String description;

        // Getters and setters
    }
}