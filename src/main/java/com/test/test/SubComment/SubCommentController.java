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

    @PostMapping("/subComment/{commentId}")
    public ResponseEntity<?> createSubComment(@PathVariable Long commentId,
                                              @RequestBody SubCommentRequestDto requestDto) {
        return ResponseEntity.ok().body(subCommentService.createSubComment(commentId, requestDto));
    }

    @GetMapping("/subComment/{commentId}")
    public ResponseEntity<?> getSubComment(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(subCommentService.getSubComment(commentId));
    }

    @PutMapping("/subComment/{subCommentId}")
    public ResponseEntity<?> updateSubComment(@PathVariable Long subCommentId,
                                              @RequestBody SubCommentRequestDto requestDto) {
        return ResponseEntity.ok().body(subCommentService.updateSubComment(subCommentId, requestDto));
    }


    @DeleteMapping("/subComment/{SubCommentId}")
    public ResponseEntity<Map<String, String>> deleteSubComment(@PathVariable Long SubCommentId)
    {   subCommentService.deleteSubComment(SubCommentId);
        return ResponseEntity.ok().body(Map.of("msg", "삭제 완료"));
    }
}
