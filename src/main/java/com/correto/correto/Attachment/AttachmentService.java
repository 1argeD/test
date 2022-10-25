package com.correto.correto.Attachment;

import com.correto.correto.Article.Article;
import com.correto.correto.Article.ArticleRepository;
import com.correto.correto.Attachment.Dto.AttachmentRequestDto;
import com.correto.correto.Attachment.Dto.AttachmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final ArticleRepository articleRepository;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    public AttachmentResponseDto createAttachment(Long articleId, AttachmentRequestDto requestDto) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        Attachment attachment = Attachment.builder()
                .article(article)
                .location(requestDto.getLocation())
                .build();

        attachmentRepository.save(attachment);
        return AttachmentResponseDto.attachment(attachment);
    }
}
