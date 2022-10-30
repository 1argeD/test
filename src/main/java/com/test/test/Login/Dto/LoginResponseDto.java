package com.test.test.Login.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponseDto {
    private String nickname;
    private Long id;
    private boolean success;
}
