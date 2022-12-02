package com.test.test.Board;

import com.test.test.Board.Dto.BoardRequestDto;
import com.test.test.Board.Dto.BoardResponseDto;
import com.test.test.Login.UserDetailsImpl;
import com.test.test.Member.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /*게시판 생성*/
    @PostMapping("/board")
    ResponseEntity<?> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestBody BoardRequestDto requestDto) {
        Member admin = userDetails.getMember();
        return ResponseEntity.ok().body(boardService.createBoard(admin, requestDto));
    }

    /*게시판 이름으로 검색하기*/
    @GetMapping("/boards/search")
    ResponseEntity<?> searchName(String boardName, Model model) {
        List<BoardResponseDto> boardList = boardService.searchName(boardName);
        model.addAttribute("board", boardList);
        return ResponseEntity.ok().body(boardService.searchName(boardName));
    }

    @DeleteMapping("/board/delete/{boardId}")
    ResponseEntity<?> deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @PathVariable Long boardId) {
        Member admin = userDetails.getMember();
        boardService.deleteBoard(admin, boardId);
        return ResponseEntity.ok().body(Map.of("msg","게시판을 삭제하였습니다."));
    }
}
