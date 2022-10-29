package com.correto.correto.Board;

import com.correto.correto.Board.Dto.BoardRequestDto;
import com.correto.correto.Board.Dto.BoardResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /*게시판 생성*/
    @PostMapping("/board")
    ResponseEntity<?> createBoard(@RequestBody BoardRequestDto requestDto) {
        return ResponseEntity.ok().body(boardService.createBoard(requestDto));
    }

    /*게시판 이름으로 검색하기*/
    @GetMapping("/boards/search")
    ResponseEntity<?> searchName(String boardName, Model model) {
        log.info(boardName);
        List<BoardResponseDto> boardList = boardService.searchName(boardName);
        model.addAttribute("board", boardList);
        return ResponseEntity.ok().body(boardService.searchName(boardName));
    }
}
