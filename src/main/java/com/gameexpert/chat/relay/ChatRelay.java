package com.gameexpert.chat.relay;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.gameexpert.chat.dto.ChatMessageResponse;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.gameexpert.chat.service.LocalChatSender;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.node.ObjectNode;

@Component
@RequiredArgsConstructor
public class ChatRelay implements MessageListener {
    public static final String CHANNEL = "webcraft:chat";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final LocalChatSender localChatSender;

    public void publish(Long worldId, Object message) {
        // TODO Lv 20: worldId와 message를 JSON으로 묶어 채팅 채널에 발행합니다.
        ObjectNode root = objectMapper.createObjectNode();
        root.put("worldId", worldId);
        ObjectNode msgNode = objectMapper.valueToTree(message);
        root.set("message", msgNode);
        String json = objectMapper.writeValueAsString(root);
        redisTemplate.convertAndSend(ChatRelay.CHANNEL, json);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // TODO Lv 20: JSON에서 worldId와 message를 읽어 localChatSender.send()로 전달합니다.
        try {
            JsonNode node = objectMapper.readTree(message.getBody());
            Long worldId = node.get("worldId").asLong();
            JsonNode jsonMessage = node.get("message");
            localChatSender.send(worldId, jsonMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
