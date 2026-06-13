package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AiService {

    // 這會去 application.properties 抓你的 API Key
    @Value("${gemini.api.key:}")
    private String apiKey;

    public String analyzeSentiment(String text) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "忘記放API Key啦"; // 如果沒設定金鑰的防呆機制
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            // Gemini API 的網址
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

            // 我們給 AI 的 Prompt 指令！這就是 Vibe Coding 的精髓！
            String prompt = "分析以下日記的情緒，只能回答『開心』、『難過』、『生氣』或『平靜』其中一個詞，不要給我其他廢話：\n" + text;
            String requestBody = "{\"contents\": [{\"parts\": [{\"text\": \"" + prompt + "\"}]}]}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // 發送請求給 Google AI
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            // 解析 AI 回傳的 JSON 答案
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            return root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText().trim();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "AI 分析失敗";
        }
    }
}