package ar.edu.undef.fie.tasksPci.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlackService {

    private final RestTemplate restTemplate;

    // Constructor para que se inyecte como constructor
    public SlackService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessage(String message) {
        String url = "https://hooks.slack.com/services/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"; // Aca va el webhook de slack, propio de mi canal o de otro (si lo quieren probar)

        //Armamos el body en un HashMap
        Map<String, String> messageBuilder = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON); // Seteamos los headers
        messageBuilder.put("text", message); // Seteamos el mensaje
        HttpEntity<Map<String, String>> request = new HttpEntity<>(messageBuilder, headers);// Unificamos los headers con el body
        restTemplate.postForEntity(url, request, String.class); // Hacemos el post
    }
}
