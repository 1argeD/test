package com.test.test.Attachment.Dto;

import com.test.test.Attachment.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentResponseDto {
    private Long id;
    private String location;
    private String created_datetime;

    public static AttachmentResponseDto attachment(Attachment attachment) {
        return AttachmentResponseDto.builder()
                .id(attachment.getId())
                .location(attachment.getLocation())
                .created_datetime(attachment.getCreated_datetime().format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .build();
    }
}
