package com.test.test;

import com.test.test.Article.Article;
import com.test.test.Article.ArticleQueryRepository;
import com.test.test.Article.ArticleRepository;
import com.test.test.Member.Member;
import com.test.test.Member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void 게시물작성() {
        String title = "게시물 이름";
        String content = "게시물 내용";

    articleRepository.save(Article.builder()
                    .title(title)
                    .content(content)
            .build());

    List<Article> articleList = articleRepository.findAll();
    Article article = articleList.get(0);

    if(!article.getTitle().equals(title)) {
        throw new RuntimeException();
    }
    if (!article.getContent().equals(content)) {
        throw new RuntimeException();
    }
    }

    @Autowired
    ArticleQueryRepository articleQueryRepository;
    @Test
    public void 날짜_검색(){
        LocalDateTime start = LocalDateTime.parse("2022-09-22 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime end = LocalDateTime.parse("2022-11-12 12:00",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        articleQueryRepository.filter(start,end);
    }


    @Test
    public void 게시판_이름으로_검색() {
        String title = "검색어";

        articleRepository.findAllByTitleContaining(title);
    }
}
