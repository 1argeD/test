package com.test.test.MyPage.Dto;

import com.test.test.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageResponseDto {
    private String nickname;
    private String email;

    public static MyPageResponseDto myProfile(Member member) {
        return MyPageResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
