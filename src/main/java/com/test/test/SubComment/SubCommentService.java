package com.test.test.SubComment;

import com.test.test.Comment.Comment;
import com.test.test.Comment.CommentRepository;
import com.test.test.Member.Member;
import com.test.test.Member.MemberRepository;
import com.test.test.SubComment.Dto.SubCommentRequestDto;
import com.test.test.SubComment.Dto.SubCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCommentService {

    private final SubCommentRepository subCommentRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    /*대댓 작성*/
    @Transactional
    public SubCommentResponseDto createSubComment(Member member, Long commentId, SubCommentRequestDto requestDto) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("대댓글을 작성하기 위해선 로그인이 필요합니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        SubComment subComment = SubComment.builder()
                .member(member)
                .comment(comment)
                .content(requestDto.getContent())
                .build();

        subCommentRepository.save(subComment);

        return SubCommentResponseDto.sub(subComment);
    }

    /*대댓 가져오기*/
    @Transactional
    public List<SubCommentResponseDto> getSubComment(Long commentId) {
        List<SubComment> subCommentList = subCommentRepository.findAllByComment_Id(commentId);
        return subCommentList.stream()
                .map(SubCommentResponseDto::sub)
                .collect(Collectors.toList());
    }

    /*대댓 수정*/
    @Transactional
    public SubCommentResponseDto updateSubComment(Member member, Long subCommentId, SubCommentRequestDto requestDto) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("대댓글을 수정하기 위해서는 로그인이 필요합니다.")
        );
        SubComment subComment = subCommentRepository.findById(subCommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다.")
        );
        if(!member.getId().equals(subComment.getMember().getId())) {
            throw new IllegalArgumentException("대댓글 작성자가 아닙니다.");
        }
        subComment.update(requestDto);
        return SubCommentResponseDto.sub(subComment);
    }

    /*대댓 삭제*/
    @Transactional
    public void deleteSubComment(Member member, Long SubCommentId) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("대댓글을 삭제하기 위해서는 로그인이 필요합니다")
        );
        SubComment subComment = subCommentRepository.findById(SubCommentId).orElseThrow(
                () ->new IllegalArgumentException("해당 대댓글이 존재하지 않습니다")
        );
        if(!member.getId().equals(subComment.getMember().getId())){
            throw new IllegalArgumentException("이 대댓글의 작성자가 아닙니다.");
        }
        subCommentRepository.delete(subComment);
    }

    /*좋아요(추천) 기능*/
    @Transactional
    public SubCommentResponseDto likeSubComment(Member member, Long subCommentId) {
        memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("대댓글 좋아요 기능을 사용하기 위해선 로그인이 필요합니다.")
        );
        SubComment subComment = subCommentRepository.findById(subCommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다")
        );
        subComment.like();
        return SubCommentResponseDto.sub(subComment);
    }
}
