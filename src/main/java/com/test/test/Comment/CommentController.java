package com.test.test.Comment;

import com.test.test.Comment.Dto.CommentRequestDto;
import com.test.test.Login.UserDetailsImpl;
import com.test.test.Member.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    /*코멘트 작성*/
    @PostMapping("/comment/{articleId}")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId,
                                           @RequestBody CommentRequestDto commentRequestDto) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(commentService.createComment(member, articleId, commentRequestDto));
    }

    /*코멘트 보기*/
    @GetMapping("/comment/{articleId}")
    public ResponseEntity<?> getComment(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(commentService.getComment(articleId));
    }

    /*코멘트 수정*/
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(commentService.updateComment(member, commentId, requestDto));
    }

    /*코멘트 삭제*/
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        Member member = userDetails.getMember();
        commentService.deleteComment(member, commentId);
        return ResponseEntity.ok().body(Map.of("msg", "댓글 삭제 완료"));
    }

    /*좋아요(추천) 기능*/
    @PostMapping("/like/{commentId}")
    public ResponseEntity<?> commentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(commentService.commentLike(member, commentId));
    }
}
