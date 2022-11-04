package com.test.test.MyPage;

import com.test.test.Article.Article;
import com.test.test.Article.ArticleRepository;
import com.test.test.Article.Dto.ArticleResponseDto;
import com.test.test.Comment.Comment;
import com.test.test.Comment.CommentRepository;
import com.test.test.Comment.Dto.CommentResponseDto;
import com.test.test.Member.Member;
import com.test.test.MyPage.Dto.MyPageResponseDto;
import com.test.test.SubComment.Dto.SubCommentResponseDto;
import com.test.test.SubComment.SubComment;
import com.test.test.SubComment.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final ArticleRepository articleRepository;

    private final CommentRepository commentRepository;

    private final SubCommentRepository subCommentRepository;

    public List<ArticleResponseDto> myArticle(Member member) {
        List<Article> myArticle = articleRepository.findAllByMember_Id(member.getId());
        return myArticle.stream()
                .map(ArticleResponseDto::Post)
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> myComment(Member member) {
        List<Comment> myComment = commentRepository.findAllByMember_Id(member.getId());
        return myComment.stream()
                .map(CommentResponseDto::comment)
                .collect(Collectors.toList());
    }

    public List<SubCommentResponseDto> mySubComment(Member member) {
        List<SubComment> mySubComment = subCommentRepository.findAllByMember_Id(member.getId());
        return mySubComment.stream()
                .map(SubCommentResponseDto::sub)
                .collect(Collectors.toList());
    }

    public MyPageResponseDto myProfile(Member member) {
        return MyPageResponseDto.myProfile(member);
    }

}

