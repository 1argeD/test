package com.correto.correto.Article;

import com.correto.correto.Article.Dto.ArticleRequestDto;
import com.correto.correto.Article.Dto.ArticleResponseDto;
import com.correto.correto.Board.Board;
import com.correto.correto.Board.BoardRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Getter
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    private final ArticleQueryRepository queryRepository;
    private final BoardRepository boardRepository;

    /*게시글 생성*/
    @Transactional
    public ArticleResponseDto createPost(Long id, ArticleRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow();
        Article article = Article.builder()
                .board(board)
                .title(requestDto.getTitle())
                .content((requestDto.getContent()))
                .build();
        repository.save(article);
        return ArticleResponseDto.Post(article);
    }

    /*게시글 수정하기 */
    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, ArticleRequestDto requestDto) {
        Article article = repository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        article.update(requestDto);
        return ArticleResponseDto.Post(article);
    }

    /*게시글 삭제*/
    @Transactional
    public void deleteArticle(Long articleId) {
        Article article = repository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("삭제할 게시물이 존재하지 않습니다.")
        );
        repository.delete(article);
    }

    /*제목으로 게시글 검색하기  */
    @Transactional
    public List<ArticleResponseDto> searchTitle(String title) {
        List<Article> titleList = repository.findAllByTitleContaining(title);
        return titleList.stream()
                .map(ArticleResponseDto :: Post)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ArticleResponseDto> searchName(String boardName) {
        List<Article> nameList = repository.getArticleByBoard_Name(boardName);
        return nameList.stream()
                .map(ArticleResponseDto :: Post)
                .collect(Collectors.toList());
    }

    /*게시글 작성 날짜로 검색하기*/
    @Transactional
    public ArticleResponseDto searchDate(String searchStartDate, String searchEndDate) {
        /*입력값이 공백일 경우*/
        if (searchStartDate == "") {
            searchStartDate = "0000-01-01 00:00";
        }
        /*입력값이 공백일 경우*/
        if (searchEndDate == "") {
            searchEndDate = "9999-12-31 00:00";
        }
        LocalDateTime startDateTime = LocalDate.parse(searchStartDate, DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm")).atTime(0, 0, 0);
        LocalDateTime endDateTime = LocalDate.parse(searchEndDate, DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm")).atTime(23, 59, 59);

        queryRepository.filter(startDateTime, endDateTime);

        return ArticleResponseDto.builder()
                .created_datetime(queryRepository.filter(startDateTime, endDateTime).getCreated_datetime())
                .id(queryRepository.filter(startDateTime, endDateTime).getId())
                .content(queryRepository.filter(startDateTime, endDateTime).getContent())
                .title(queryRepository.filter(startDateTime, endDateTime).getTitle())
                .build();
    }

}
