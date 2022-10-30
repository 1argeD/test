package com.test.test.Member;

import com.test.test.Login.Dto.LoginRequestDto;
import com.test.test.Login.Dto.SignupRequestDto;
import com.test.test.Member.dto.NicknameCheckRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) {
        memberService.signup(requestDto);
        return ResponseEntity.ok().body(Map.of("msg", "회원가입 완료"));
    }

    @PostMapping("/member/emailCheck")
    public ResponseEntity<?> checkEmail(String email) {
        memberService.checkEmailIsDuplication(email);
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 닉네임입니다."));
    }

    @PostMapping("/member/nicknameCheck")
    public ResponseEntity<?>nicknameCheck(@RequestBody NicknameCheckRequestDto requestDto) {
        memberService.checkNicknameIsDuplicate(requestDto.getNickname());
        return ResponseEntity.ok().body(Map.of("msg","사용가능한 닉네입입니다"));
    }

    @PostMapping("member/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(memberService.login(loginRequestDto, response));
    }
}
