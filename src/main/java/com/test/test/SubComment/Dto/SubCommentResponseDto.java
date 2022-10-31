package com.test.test.SubComment.Dto;

import com.test.test.SubComment.SubComment;
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
public class SubCommentResponseDto {
    private Long id;
    private String content;
    private Long commentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created_datetime;
    private int likeCnt;

    public static SubCommentResponseDto sub(SubComment subComment) {
        return SubCommentResponseDto.builder()
                .id(subComment.getId())
                .commentId(subComment.getComment().getId())
                .content(subComment.getContent())
                .likeCnt(subComment.getLikeCnt())
                .created_datetime(subComment.getCreated_datetime())
                .build();
    }
}
