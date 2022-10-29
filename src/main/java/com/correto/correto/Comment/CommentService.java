package com.correto.correto.Comment;

import com.correto.correto.Article.Article;
import com.correto.correto.Article.ArticleRepository;
import com.correto.correto.Comment.Dto.CommentRequestDto;
import com.correto.correto.Comment.Dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(Long articleId, CommentRequestDto requestDto) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Comment comment = Comment.builder()
                .article(article)
                .content(requestDto.getContent())
                .build();
        commentRepository.save(comment);
        log.info(comment.toString());
        return CommentResponseDto.comment(comment);
    }

    @Transactional
    public List<CommentResponseDto> getComment(Long articleId) {
        List<Comment> comments = commentRepository.findAllByArticle_Id(articleId);
        return comments.stream()
                .map(CommentResponseDto::comment)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long CommentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(CommentId).orElseThrow();
        comment.updateComment(requestDto);
        return CommentResponseDto.comment(comment);
    }

    @Transactional
    public void deleteComment(Long CommentId) {
        Comment comment = commentRepository.findById(CommentId).orElseThrow();
        commentRepository.delete(comment);
    }
}
