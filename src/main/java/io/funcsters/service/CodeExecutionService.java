package io.funcsters.service;

import io.funcsters.dto.CodeExecutionRequest;
import io.funcsters.dto.Judge0Response;

import java.util.List;

public interface CodeExecutionService {
    Judge0Response runCode(String problemId, CodeExecutionRequest request);

    List<Judge0Response> runAllLanguagesForProblem(String problemId);
}
