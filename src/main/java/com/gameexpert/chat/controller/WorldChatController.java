package com.gameexpert.chat.controller;

import java.util.List;

import lombok.Builder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gameexpert.chat.dto.ChatMessageResponse;
import com.gameexpert.chat.service.RecentChatQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WorldChatController {

    private final RecentChatQueryService chatService;

    // TODO Lv 6: API 명세에 맞는 요청 매핑과 응답을 구현합니다.
    @GetMapping("/worlds/{worldId}/chats")
    public ResponseEntity<List<ChatMessageResponse>> chats(@PathVariable Long worldId, @RequestParam(defaultValue = "50") int limit) {
        List<ChatMessageResponse> recentChatResponses = chatService.getRecentMessages(worldId, limit);
        return ResponseEntity.ok(recentChatResponses);
    }
}
