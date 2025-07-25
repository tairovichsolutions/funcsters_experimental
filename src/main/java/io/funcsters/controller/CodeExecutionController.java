package io.funcsters.controller;

import io.funcsters.dto.CodeExecutionRequest;
import io.funcsters.dto.Judge0Response;
import io.funcsters.service.CodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class CodeExecutionController {

    private final CodeExecutionService executionService;

    @PostMapping("/{problemId}/run")
    public ResponseEntity<Judge0Response> runCode(@PathVariable String problemId,
                                                  @RequestBody CodeExecutionRequest request) {
        Judge0Response judge0Response = executionService.runCode(problemId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(judge0Response);
    }

    @PostMapping("/{problemId}/validate")
    public ResponseEntity<List<Judge0Response>> runCodeWithTestCases(@PathVariable String problemId) {
        List<Judge0Response> judge0Responses = executionService.runAllLanguagesForProblem(problemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(judge0Responses);
    }
}