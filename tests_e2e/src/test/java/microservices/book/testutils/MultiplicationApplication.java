package microservices.book.testutils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author moises.macero
 */
public class MultiplicationApplication {

    private static final String APPLICATION_BASE_URL = "http://localhost:8000/api";
    private static final String CONTEXT_ATTEMPTS = "/results";
    private static final String CONTEXT_SCORE = "/scores/";
    private static final String CONTEXT_STATS = "/stats";
    private static final String CONTEXT_DELETE_DATA_GAM = "/gamification/admin/delete-db";
    private static final String CONTEXT_DELETE_DATA_MULT = "/multiplication/admin/delete-db";

    private ApplicationHttpUtils httpUtils;

    public MultiplicationApplication() {
        this.httpUtils = new ApplicationHttpUtils(APPLICATION_BASE_URL);
    }

    public AttemptResponse sendAttempt(String userAlias, int factorA, int factorB, int result) {
        String attemptJson = "{\"user\":{\"alias\":\"" + userAlias + "\"}," +
                "\"multiplication\":{\"factorA\":\"" + factorA + "\",\"factorB\":\"" + factorB + "\"}," +
                "\"resultAttempt\":\"" + result + "\"}";
        String response = httpUtils.post(CONTEXT_ATTEMPTS, attemptJson);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(response, AttemptResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ScoreResponse getScoreForAttempt(long attemptId) {
        String response = httpUtils.get(CONTEXT_SCORE + attemptId);
        if (response.isEmpty()) {
            return new ScoreResponse(0);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                return objectMapper.readValue(response, ScoreResponse.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Stats getStatsForUser(long userId) {
        String response = httpUtils.get(CONTEXT_STATS + "?userId=" + userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(response, Stats.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData() {
        httpUtils.post(CONTEXT_DELETE_DATA_GAM, "");
        httpUtils.post(CONTEXT_DELETE_DATA_MULT, "");
    }
}
