package com.exercise.project.controller.ai;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Ai Chat")
@RequestMapping("${project.api.prefix}/ai")
public class AiChatController {

    private final ChatModel chatModel;

    @PostMapping
    public Map<String, String> chatWithRequestBody(
        @RequestBody Map<String, String> request
    ) {
        String userPrompt = request.getOrDefault("message", "Hello");
        String response = ChatClient.create(chatModel)
            .prompt()
            .user(userPrompt)
            .call()
            .content();

        return Map.of("prompt", userPrompt, "response", response);
    }
}
