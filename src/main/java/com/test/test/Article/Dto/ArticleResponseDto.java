package com.test.test.Article.Dto;

import com.test.test.Article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private int viewCnt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created_datetime;


    public static ArticleResponseDto Post(Article article) {
        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .viewCnt(article.getViewCount())
                .created_datetime(article.getCreated_datetime())
                .build();
    }
}
