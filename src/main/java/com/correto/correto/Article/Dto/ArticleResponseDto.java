package com.correto.correto.Article.Dto;

import com.correto.correto.Article.Article;
import com.correto.correto.Board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String created_datetime;

    public static ArticleResponseDto Post(Article article) {
        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .created_datetime(article.getCreated_datetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
