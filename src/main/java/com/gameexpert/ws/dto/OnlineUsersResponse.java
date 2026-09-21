package com.gameexpert.ws.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class OnlineUsersResponse {
    // TODO Lv 15: API 명세에 맞게 응답 필드와 생성자를 완성합니다.

    private String type;
    private List<String> users;
    private int count;

    public OnlineUsersResponse(List<String> users) {
        type = "onlineUsers";
        this.users = List.copyOf(users);
        this.count = this.users.size();
    }
}
