package com.test.test.Comment;

import com.test.test.Comment.Dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    /*코멘트 작성*/
    @PostMapping("/comment/{articleId}")
    public ResponseEntity<?> createComment(@PathVariable Long articleId,
                                           @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok().body(commentService.createComment(articleId, commentRequestDto));
    }

    /*코멘트 보기*/
    @GetMapping("/comment/{articleId}")
    public ResponseEntity<?> getComment(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(commentService.getComment(articleId));
    }

    /*코멘트 수정*/
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok().body(commentService.updateComment(commentId, requestDto));
    }

    /*코멘트 삭제*/
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(Map.of("msg", "댓글 삭제 완료"));
    }

    /*좋아요(추천) 기능*/
    @PostMapping("/like/{commentId}")
    public ResponseEntity<?> commentLike(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(commentService.commentLike(commentId));
    }
}
