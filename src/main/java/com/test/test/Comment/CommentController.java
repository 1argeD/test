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

    @PostMapping("/comment/{articleId}")
    public ResponseEntity<?> createComment(@PathVariable Long articleId,
                                           @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok().body(commentService.createComment(articleId, commentRequestDto));
    }

    @GetMapping("/comment/{articleId}")
    public ResponseEntity<?> getComment(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(commentService.getComment(articleId));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok().body(commentService.updateComment(commentId, requestDto));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(Map.of("msg", "댓글 삭제 완료"));
    }
}
