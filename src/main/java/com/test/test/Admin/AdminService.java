package com.test.test.Admin;

import com.test.test.Article.ArticleRepository;
import com.test.test.Member.Member;
import com.test.test.Member.MemberRepository;
import com.test.test.Member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    /* 모든 Member 정보 가져오기*/
    @Transactional
    public List<MemberResponseDto> memberList(Member admin) {
    Member chkAdmin = memberRepository.findById(admin.getId()).orElseThrow();
    if(!chkAdmin.getRole().equals("ROLE_ADMIN")) {
        throw new RequestRejectedException("해당 권환을 가지고 있지 않습니다");
    }
    List<Member>members = memberRepository.findAll();
    return members.stream()
            .map(MemberResponseDto :: memberList)
            .collect(Collectors.toList());
    }

    /* 게시글 전체 삭제 */
    @Transactional
    public void deleteAll(Member admin) {
        Member chkAdmin = memberRepository.findById(admin.getId()).orElseThrow();
        if(!chkAdmin.getRole().equals("ROLE_ADMIN")) {
            throw new RequestRejectedException("해당 권환을 가지고 있지 않습니다");
        }
        articleRepository.deleteAll();
    }
}
