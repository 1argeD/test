package com.test.test.Member;

import com.test.test.Login.Dto.LoginRequestDto;
import com.test.test.Login.Dto.LoginResponseDto;
import com.test.test.Login.Dto.SignupRequestDto;
import com.test.test.Login.JWT.JwtFilter;
import com.test.test.Login.JWT.JwtProvider;
import com.test.test.Login.RefreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;
    /*회원가입*/
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        checkEmailIsDuplication(requestDto.getEmail());
        String encodingPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = new Member(requestDto.getEmail(), requestDto.getNickname(), encodingPassword);
        memberRepository.save(member);
    }
    /*중복이메일 체크*/
    public void checkEmailIsDuplication(String email) {
        boolean isDuplication = memberRepository.existsByEmail(email);
        if(isDuplication) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
    /*닉네임 중복 체크*/
    public void checkNicknameIsDuplicate(String nickname) {
        boolean isDuplicate = memberRepository.existsByNickname(nickname);
        if(isDuplicate) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }
    /*로그인*/
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("아이디 혹은 비밀 번호를 확인하세요."));
        checkPassword(loginRequestDto.getPassword(), member.getPassword());

        String accessToken = jwtProvider.createAuthorizationToken(member.getEmail(), member.getRole());
        String refreshToken = jwtProvider.createRefreshToken(member, member.getRole());
        tokenToHeaders(accessToken, refreshToken, response);
        return new LoginResponseDto(member.getNickname(), member.getId(), true);
    }
    /*비밀번호 확인*/
    private void checkPassword(String password, String encodingPassword) {
        boolean isSome = passwordEncoder.matches(password, encodingPassword);
        if(!isSome) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호를 확인하세요");
        }
    }
    /*헤더에 토큰 담기*/
    public void tokenToHeaders(String authorizationToken, String refreshToken, HttpServletResponse response) {
        response.addHeader("Authorization", JwtFilter.BEARER_PREFIX +authorizationToken);
        response.addHeader("RefreshToken", refreshToken);
    }
    /*로그아웃*/
    @Transactional
    public void logout(Member member) {
        refreshTokenRepository.deleteByMember(member);
    }
}