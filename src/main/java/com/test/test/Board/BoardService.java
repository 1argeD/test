package com.test.test.Board;


import com.test.test.Board.Dto.BoardRequestDto;
import com.test.test.Board.Dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    /*게시판 생성*/
    @Transactional
    BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = Board.builder()
                .name(requestDto.getName())
                .build();
        repository.save(board);
        return BoardResponseDto.BoardP(board);
    }

    /*게시판 이름으로 검색*/
    @Transactional
    public List<BoardResponseDto> searchName(String boardName) {
        List<Board> nameList = repository.findAllByNameContaining(boardName);
        log.info(nameList.toString());
        return nameList.stream()
                .map(BoardResponseDto::BoardP)
                .collect(Collectors.toList());
    }
}
