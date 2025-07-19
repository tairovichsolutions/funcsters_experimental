package io.funcsters.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.funcsters.dto.*;
import io.funcsters.exceptions.CodeExecutionException;
import io.funcsters.service.CodeExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeExecutionServiceImpl implements CodeExecutionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${judge0.api.url:http://localhost:2358/submissions?wait=true}")
    private String judge0ApiUrl;

    @Override
    public Judge0Response runCode(String problemId, CodeExecutionRequest request) {

        ProblemData problemData = loadProblemData(problemId)
                .orElseThrow(() -> new IllegalArgumentException("Problem data not found for: " + problemId));

        String languageKey = Optional.ofNullable(request.getLanguage())
                .orElseThrow(() -> new IllegalArgumentException("Language must be specified in the request."));
        ProblemData.Language language = problemData.getLanguage().get(languageKey);

        if (language == null) {
            throw new IllegalArgumentException("Language " + languageKey + " not supported for this problem.");
        }

        String sourceCode = language.getWrapper() + "\n" + request.getUserCode();

        Judge0SubmissionRequest submission = Judge0SubmissionRequest.builder()
                .sourceCode(sourceCode)
                .languageId(language.getLanguageId())
                .stdin(problemData.getTestCases())
                .build();

        return getJudge0ResponseResponseEntity(submission);
    }

    private Judge0Response getJudge0ResponseResponseEntity(Judge0SubmissionRequest submission) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBodyJson;
        try {
            requestBodyJson = objectMapper.writeValueAsString(submission);
        } catch (Exception e) {
            System.err.println("Failed to serialize submission to JSON: " + e.getMessage());
            throw new RuntimeException("Failed to serialize submission to JSON", e);
        }

        System.out.println("Submitting to Judge0: " + requestBodyJson);

        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);

        try {
            ResponseEntity<Judge0Response> response = restTemplate.postForEntity(
                    judge0ApiUrl,
                    entity,
                    Judge0Response.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error communicating with Judge0 API: " + e.getMessage());
            throw new RuntimeException("Error communicating with Judge0 API", e);
        }
    }
    private Judge0Response getJudge0Response(Judge0SubmissionRequest submission) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBodyJson;
        try {
            requestBodyJson = objectMapper.writeValueAsString(submission);
        } catch (Exception e) {
            log.error("Failed to serialize submission to JSON", e);
            throw new CodeExecutionException("Failed to serialize submission to JSON", e);
        }

        log.info("Submitting to Judge0: {}", requestBodyJson);

        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);

        try {
            ResponseEntity<Judge0Response> response = restTemplate.postForEntity(
                    judge0ApiUrl,
                    entity,
                    Judge0Response.class
            );
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Non-successful response from Judge0: {} - {}", response.getStatusCode(), response.getBody());
                throw new CodeExecutionException("Judge0 API error: " + response.getStatusCode());
            }
            if (response.getBody() == null) {
                log.error("Judge0 API returned null body.");
                throw new CodeExecutionException("Judge0 API returned null body.");
            }
            return response.getBody();
        } catch (Exception e) {
            log.error("Error communicating with Judge0 API", e);
            throw new CodeExecutionException("Error communicating with Judge0 API", e);
        }
    }


    @Override
    public List<Judge0Response> runAllLanguagesForProblem(String problemId) {

        List<Judge0Response> responses = new ArrayList<>();

        ProblemData problemData = loadProblemData(problemId)
                .orElseThrow(() -> new IllegalArgumentException("Problem data not found for: " + problemId));

        problemData.getLanguage().forEach((languageKey, language) -> {
            String sourceCode = language.getWrapper() + "\n" + language.getSolution();
            Integer languageId = language.getLanguageId();

            Judge0SubmissionRequest request = Judge0SubmissionRequest.builder()
                    .sourceCode(sourceCode)
                    .languageId(languageId)
                    .stdin(problemData.getTestCases())
                    .build();
            Judge0Response response = getJudge0ResponseResponseEntity(request);
            responses.add(response);
        });
        return responses;
    }

    private Optional<ProblemData> loadProblemData(String problemTitle) {
        String fileName = "wrappers/" + problemTitle + ".json";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) return Optional.empty();
            return Optional.of(objectMapper.readValue(inputStream, ProblemData.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load problem data: " + fileName, e);
        }
    }
}