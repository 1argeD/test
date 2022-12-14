package com.test.test.SubComment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.test.Comment.Comment;
import com.test.test.Member.Member;
import com.test.test.SubComment.Dto.SubCommentRequestDto;
import com.test.test.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SubComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private int likeCnt;

    private boolean upDate;

    public void update(SubCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.upDate = true;
    }

    public void like() {
        this.likeCnt = likeCnt + 1;
    }
}
