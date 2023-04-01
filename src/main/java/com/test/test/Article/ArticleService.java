package com.test.test.Article;

import com.test.test.Article.Dto.ArticleRequestDto;
import com.test.test.Article.Dto.ArticleResponseDto;
import com.test.test.Board.Board;
import com.test.test.Board.BoardRepository;
import com.test.test.Member.Member;
import com.test.test.Member.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    private final ArticleQueryRepository queryRepository;
    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    /*게시글 생성*/
    @Transactional
    public ArticleResponseDto createPost(Member member, Long id, ArticleRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow();
        Article article = Article.builder()
                .member(member)
                .board(board)
                .title(requestDto.getTitle())
                .content((requestDto.getContent()))
                .build();
        repository.save(article);
        return ArticleResponseDto.Post(article);
    }

    /*게시글 수정하기 */
    @Transactional
    public ArticleResponseDto updateArticle(Member member, Long articleId, ArticleRequestDto requestDto) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        Article article = repository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        if("ROLE_ADMIN".equals(member.getRole())){
            article.update(requestDto);
        }else if(!member.getId().equals(article.getMember().getId())) {
            throw new IllegalArgumentException("게시글 작성자가 아닙니다.");
        }
        article.update(requestDto);
        return ArticleResponseDto.Post(article);
    }

    /*게시글 삭제*/
    @Transactional
    public void deleteArticle(Member member, Long articleId) {
        Article article = repository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("삭제할 게시물이 존재하지 않습니다.")
        );
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다")
        );
        if("ROLE_ADMIN".equals(member.getRole())) {
            repository.delete(article);
        } else if  (!Objects.equals(article.getMember().getId(), member.getId())) {
            throw new IllegalArgumentException("게시글 작성자가 아닙니다.");
        }
        repository.delete(article);
    }

    /*제목으로 게시글 검색하기  */
    @Transactional
    public List<ArticleResponseDto> searchTitle(String title) {
        List<Article> titleList = repository.findAllByTitleContaining(title);
        return titleList.stream()
                .map(ArticleResponseDto::Post)
                .collect(Collectors.toList());
    }

    /*게시판 이름으로 검색*/
    @Transactional
    public List<ArticleResponseDto> searchName(String boardName) {
        List<Article> nameList = repository.findArticleByBoard_Name(boardName);
        return nameList.stream()
                .map(ArticleResponseDto::Post)
                .collect(Collectors.toList());
    }

    /*게시글 작성 날짜로 검색하기*/
    @Transactional
    public List<ArticleResponseDto> searchDate(String searchStartDate, String searchEndDate) {
        /*입력값이 공백일 경우*/
        if (searchStartDate.equals("")) searchStartDate = "0001-01-01";
        /*입력값이 공백일 경우*/
        if (searchEndDate.equals("")) searchEndDate = "9999-12-31";
        /*String 값을 LocalDateTime 으로 변환*/
        LocalDateTime startDateTime = LocalDate.parse(searchStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(0, 0, 0);
        LocalDateTime endDateTime = LocalDate.parse(searchEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);

        return queryRepository.filter(startDateTime, endDateTime);
    }

    /*게시글 상세 조회*/
    @Transactional
    public ArticleResponseDto view(Long articleId) {
        Article article = repository.findById(articleId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글을 조회할 수 없습니다.")
        );
        article.view();
        return ArticleResponseDto.Post(article);
    }

    /*좋아요(추천) 기능*/
    @Transactional
    public ArticleResponseDto articleLike(Member member, Long articleId) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("로그인 후 이용해주세요")
        );
        Article article = repository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("좋아요 할 게시물을 찾을 수 없습니다")
        );
        article.like();
        return ArticleResponseDto.Post(article);
    }
}
