package com.test.test.SubComment;

import com.test.test.Comment.Comment;
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

    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    private int likeCnt;

    public void update(SubCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void like() {
        this.likeCnt = likeCnt + 1;
    }
}
