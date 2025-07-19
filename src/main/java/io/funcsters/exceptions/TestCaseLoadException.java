package io.funcsters.exceptions;

public class TestCaseLoadException extends RuntimeException{

    public TestCaseLoadException(String message) {
        super(message);
    }

    public TestCaseLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}