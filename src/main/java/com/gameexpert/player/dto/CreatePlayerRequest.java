package com.gameexpert.player.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreatePlayerRequest {

    // TODO Lv 3: 2~12글자의 영문 대소문자, 숫자와 밑줄을 허용하는 검증을 적용합니다.
    @Pattern(regexp = "^[a-zA-Z0-9_]{2,12}$", message = "닉네임에는 2~12글자의 영문 대소문자, 숫자, 밑줄만 입력할 수 있습니다.")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private final String nickname;

    public CreatePlayerRequest(String nickname) {
        this.nickname = nickname;
    }
}
