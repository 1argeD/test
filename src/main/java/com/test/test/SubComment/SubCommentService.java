package com.test.test.SubComment;

import com.test.test.Comment.Comment;
import com.test.test.Comment.CommentRepository;
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

    /*대댓 작성*/
    @Transactional
    public SubCommentResponseDto createSubComment(Long commentId, SubCommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        SubComment subComment = SubComment.builder()
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
    public SubCommentResponseDto updateSubComment(Long subCommentId, SubCommentRequestDto requestDto) {
        SubComment subComment = subCommentRepository.findById(subCommentId).orElseThrow();
        subComment.update(requestDto);
        return SubCommentResponseDto.sub(subComment);
    }

    /*대댓 삭제*/
    @Transactional
    public void deleteSubComment(Long SubCommentId) {
        SubComment subComment = subCommentRepository.findById(SubCommentId).orElseThrow();
        subCommentRepository.delete(subComment);
    }

    /*좋아요(추천) 기능*/
    @Transactional
    public SubCommentResponseDto likeSubComment(Long subCommentId) {
        SubComment subComment = subCommentRepository.findById(subCommentId).orElseThrow();
        subComment.like();
        return SubCommentResponseDto.sub(subComment);
    }
}