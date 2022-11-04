package com.test.test.Comment.Dto;

import com.test.test.Comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created_datetime;
    private Long article_id;

    private int likeCnt;

    private boolean upDate;

    public static CommentResponseDto comment(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .article_id(comment.getArticle().getId())
                .likeCnt(comment.getLikeCnt())
                .created_datetime(comment.getCreated_datetime())
                .upDate(comment.isUpDate())
                .build();
    }
}
