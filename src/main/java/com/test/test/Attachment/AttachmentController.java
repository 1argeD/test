package com.test.test.Attachment;

import com.test.test.Attachment.Dto.AttachmentRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/attachment/{articleId}")
    ResponseEntity<?> createAttachment(@PathVariable Long articleId, @RequestBody AttachmentRequestDto requestDto) {
        return ResponseEntity.ok().body(attachmentService.createAttachment(articleId, requestDto));
    }
}
