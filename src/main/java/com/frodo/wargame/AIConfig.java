package com.frodo.wargame;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {


    @Bean
    public ChatClient chatClient(AzureOpenAiChatModel model) {
        return ChatClient.builder(model).build();
    }

}