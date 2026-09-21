package com.gameexpert.ws.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ChatResponse {
    // TODO Lv 13: API 명세에 맞게 응답 필드와 생성자를 완성합니다.
    private String type;
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    public ChatResponse(String sender, String content, LocalDateTime timestamp) {
        type = "chat";
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }
}
