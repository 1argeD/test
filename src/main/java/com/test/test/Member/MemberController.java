package com.test.test.Member;

import com.test.test.Exception.BadRequestException;
import com.test.test.Login.Dto.LoginRequestDto;
import com.test.test.Login.Dto.SignupRequestDto;
import com.test.test.Login.UserDetailsImpl;
import com.test.test.Member.dto.NicknameCheckRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    /*회원가입*/
    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) throws BadRequestException {
        memberService.signup(requestDto);
        return ResponseEntity.ok().body(Map.of("msg", "회원가입 완료"));
    }
    /*이메일 체크*/
    @PostMapping("/member/emailCheck")
    public ResponseEntity<?> checkEmail(String email) throws BadRequestException {
        memberService.checkEmailIsDuplication(email);
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 이메일입니다."));
    }
    /*닉네임 체크*/
    @PostMapping("/member/nicknameCheck")
    public ResponseEntity<?>nicknameCheck(@RequestBody NicknameCheckRequestDto requestDto) throws BadRequestException {
        memberService.checkNicknameIsDuplicate(requestDto.getNickname());
        return ResponseEntity.ok().body(Map.of("msg","사용가능한 닉네입입니다"));
    }
    /*로그인*/
    @PostMapping("member/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) throws BadRequestException {
        return ResponseEntity.ok().body(memberService.login(loginRequestDto, response));
    }
    /*로그아웃*/
    @PostMapping("member/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        memberService.logout(member);
        return ResponseEntity.ok().body(Map.of("msg","로그아웃 성공"));
    }

    @DeleteMapping("member/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LoginRequestDto loginRequestDto) throws BadRequestException {
        memberService.withdraw(userDetails, loginRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("member/admin")
    public ResponseEntity<?> admin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        memberService.admin(member);
        return ResponseEntity.ok().body(Map.of("msg", "role을 admin으로 변경하였습니다"));
    }
}
