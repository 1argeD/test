package com.test.test.Login;

import com.test.test.Member.Member;
import com.test.test.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    /*이메일이 일치하지 않으면 오류 발생*/
    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        Optional<Member>member = memberRepository.findByEmail(memberEmail);
        return member
                .map(UserDetailsImpl :: new)
                .orElseThrow(()-> new UsernameNotFoundException("아이디를 찾을 수 없습니다"));
    }
}
