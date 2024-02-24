package ar.edu.undef.fie.tasksPci.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SlackServiceTest {

    @Test
    void sendMessage() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        SlackService slackService = new SlackService(restTemplate);

        String message = "Test Message";
        String url = "https://hooks.slack.com/services/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";

        Map<String, String> messageBuilder = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        messageBuilder.put("text", message);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(messageBuilder, headers);

        slackService.sendMessage(message);

        verify(restTemplate, times(1)).postForEntity(url, request, String.class);
    }
}