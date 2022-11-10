package com.test.test;

import com.test.test.Member.Member;
import com.test.test.Member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class testApplicationTests {
    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원가입() {
    String email = "qwe@naver.com";
    String nickname = "asdff";
    String password = "kkk";

    memberRepository.save(Member.builder()
                    .email(email)
                    .nickname(nickname)
                    .password(password)
            .build());

        List<Member>memberList = memberRepository.findAll();

        Member member = memberList.get(0);
    if(!member.getEmail().equals(email))
    {
        throw new IllegalArgumentException();
    }
    if(!member.getNickname().equals(nickname)) {
        throw new IllegalArgumentException();
        }
    }

}
