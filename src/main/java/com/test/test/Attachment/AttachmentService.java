package com.test.test.Attachment;


import com.test.test.Article.Article;
import com.test.test.Article.ArticleRepository;
import com.test.test.Attachment.Dto.AttachmentRequestDto;
import com.test.test.Attachment.Dto.AttachmentResponseDto;
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
