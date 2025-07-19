package io.funcsters.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(TestCaseLoadException.class)
     public ResponseEntity<String> handleTestCaseLoadException(TestCaseLoadException ex) {
            log.error("Test case loading error: {}", ex.getMessage(), ex);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
     }

        @ExceptionHandler(CodeExecutionException.class)
        public ResponseEntity<String> handleCodeExecutionException(CodeExecutionException ex) {
            log.error("Code execution error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
}
