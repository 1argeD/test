package com.test.test.Comment;

import com.test.test.Article.Article;
import com.test.test.Article.ArticleRepository;
import com.test.test.Comment.Dto.CommentRequestDto;
import com.test.test.Comment.Dto.CommentResponseDto;
import com.test.test.Member.Member;
import com.test.test.Member.MemberRepository;
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

    private final MemberRepository memberRepository;


    /*코멘트 작성*/
    @Transactional
    public CommentResponseDto createComment(Member member, Long articleId, CommentRequestDto requestDto) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("코멘트를 작성하려면 로그인을 해주세요.")
        );
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다")
        );
        Comment comment = Comment.builder()
                .member(member)
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
    public CommentResponseDto updateComment(Member member, Long CommentId, CommentRequestDto requestDto) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("댓글을 수정하기 위해서는 로그인이 필요합니다.")
        );
        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 코멘트를 찾을 수 없습니다.")
        );
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new IllegalArgumentException("자신이 작성한 댓글이 아닙니다.");
        }
        comment.updateComment(requestDto);
        return CommentResponseDto.comment(comment);
    }

    /*코멘트 삭제*/
    @Transactional
    public void deleteComment(Member member, Long CommentId) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("댓글을 삭제하려면 로그인을 해주세요.")
        );
        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 코멘트를 찾을 수 없습니다.")
        );
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }
        commentRepository.delete(comment);
    }

    /*좋아요(추천) 기능*/
    @Transactional
    public CommentResponseDto commentLike(Member member, Long commentId) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("좋아요 서비스는 로그인이 필요합니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 코멘트를 찾을 수 없습니다.")
        );
        comment.like();
        return CommentResponseDto.comment(comment);
    }
}
