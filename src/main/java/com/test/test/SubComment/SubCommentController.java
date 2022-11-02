package com.test.test.SubComment;

import com.test.test.Login.UserDetailsImpl;
import com.test.test.Member.Member;
import com.test.test.SubComment.Dto.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class SubCommentController {

    private final SubCommentService subCommentService;

    /*대댓 작성*/
    @PostMapping("/subComment/{commentId}")
    public ResponseEntity<?> createSubComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PathVariable Long commentId,
                                              @RequestBody SubCommentRequestDto requestDto) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(subCommentService.createSubComment(member, commentId, requestDto));
    }

    /*대댓 가져오기*/
    @GetMapping("/subComment/{commentId}")
    public ResponseEntity<?> getSubComment(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(subCommentService.getSubComment(commentId));
    }

    /*대댓 수정*/
    @PutMapping("/subComment/{subCommentId}")
    public ResponseEntity<?> updateSubComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PathVariable Long subCommentId,
                                              @RequestBody SubCommentRequestDto requestDto) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(subCommentService.updateSubComment(member, subCommentId, requestDto));
    }

    /*대댓 삭제*/
    @DeleteMapping("/subComment/{SubCommentId}")
    public ResponseEntity<Map<String, String>> deleteSubComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                @PathVariable Long SubCommentId) {
        Member member = userDetails.getMember();
        subCommentService.deleteSubComment(member, SubCommentId);
        return ResponseEntity.ok().body(Map.of("msg", "삭제 완료"));
    }

    /*좋아요(추천) 기능*/
    @PostMapping("/subLike/{subCommentId}")
    public ResponseEntity<?> likeSubComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long subCommentId) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(subCommentService.likeSubComment(member, subCommentId));
    }
}
