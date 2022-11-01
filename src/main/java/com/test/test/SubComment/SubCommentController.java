package com.test.test.SubComment;

import com.test.test.SubComment.Dto.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class SubCommentController {

    private final SubCommentService subCommentService;

    /*대댓 작성*/
    @PostMapping("/subComment/{commentId}")
    public ResponseEntity<?> createSubComment(@PathVariable Long commentId,
                                              @RequestBody SubCommentRequestDto requestDto) {
        return ResponseEntity.ok().body(subCommentService.createSubComment(commentId, requestDto));
    }

    /*대댓 가져오기*/
    @GetMapping("/subComment/{commentId}")
    public ResponseEntity<?> getSubComment(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(subCommentService.getSubComment(commentId));
    }

    /*대댓 수정*/
    @PutMapping("/subComment/{subCommentId}")
    public ResponseEntity<?> updateSubComment(@PathVariable Long subCommentId,
                                              @RequestBody SubCommentRequestDto requestDto) {
        return ResponseEntity.ok().body(subCommentService.updateSubComment(subCommentId, requestDto));
    }

    /*대댓 삭제*/
    @DeleteMapping("/subComment/{SubCommentId}")
    public ResponseEntity<Map<String, String>> deleteSubComment(@PathVariable Long SubCommentId) {
        subCommentService.deleteSubComment(SubCommentId);
        return ResponseEntity.ok().body(Map.of("msg", "삭제 완료"));
    }

    /*좋아요(추천) 기능*/
    @PostMapping("/subLike/{subCommentId}")
    public ResponseEntity<?> likeSubComment(@PathVariable Long subCommentId) {
        return ResponseEntity.ok().body(subCommentService.likeSubComment(subCommentId));
    }
}
