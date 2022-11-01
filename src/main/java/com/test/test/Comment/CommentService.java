package com.test.test.Comment;

import com.test.test.Article.Article;
import com.test.test.Article.ArticleRepository;
import com.test.test.Comment.Dto.CommentRequestDto;
import com.test.test.Comment.Dto.CommentResponseDto;
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

    /*코멘트 작성*/
    @Transactional
    public CommentResponseDto createComment(Long articleId, CommentRequestDto requestDto) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Comment comment = Comment.builder()
                .article(article)
                .content(requestDto.getContent())
                .build();
        commentRepository.save(comment);
        return CommentResponseDto.comment(comment);
    }

    /*코멘트 목록 가져오기*/
    @Transactional
    public List<CommentResponseDto> getComment(Long articleId) {
        List<Comment> comments = commentRepository.findAllByArticle_Id(articleId);
        return comments.stream()
                .map(CommentResponseDto::comment)
                .collect(Collectors.toList());
    }

    /*코멘트 수정하기*/
    @Transactional
    public CommentResponseDto updateComment(Long CommentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(CommentId).orElseThrow();
        comment.updateComment(requestDto);
        return CommentResponseDto.comment(comment);
    }

    /*코멘트 삭제*/
    @Transactional
    public void deleteComment(Long CommentId) {
        Comment comment = commentRepository.findById(CommentId).orElseThrow();
        commentRepository.delete(comment);
    }

    /*좋아요(추천) 기능*/
    @Transactional
    public CommentResponseDto commentLike(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.like();
        return CommentResponseDto.comment(comment);
    }
}
