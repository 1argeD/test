package com.test.test.Board;


import com.test.test.Board.Dto.BoardRequestDto;
import com.test.test.Board.Dto.BoardResponseDto;
import com.test.test.Member.Member;
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
    BoardResponseDto createBoard(Member admin, BoardRequestDto requestDto) {
        if(!"ROLE_ADMIN".equals(admin.getRole())) {
            throw new IllegalArgumentException("게시판을 생성하기 위해선 관리자 권한이 필요합니다.");
        }
        Board board = Board.builder()
                .name(requestDto.getName())
                .build();
        repository.save(board);
        return BoardResponseDto.BoardP(board);
    }

    @Transactional
    public void deleteBoard(Member admin, Long boardId) {
        if(!"ROLE_ADMIN".equals(admin.getRole())) {
            throw new IllegalArgumentException("게시판을 삭제하기 위해선 관리자 권한이 필요합니다");
        }
        Board board = repository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다.")
        );
        repository.delete(board);
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
