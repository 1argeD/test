package com.correto.correto.Board.Dto;

import com.correto.correto.Board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
    private Long id;
    private String name;

    public static BoardResponseDto BoardP(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .name(board.getName())
                .build();
    }
}
