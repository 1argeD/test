package com.correto.correto.Attachment.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentRequestDto {
    private Long article_id;
    private String location;
    private String created_datetime;
}
