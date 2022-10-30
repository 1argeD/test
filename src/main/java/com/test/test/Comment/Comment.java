package com.test.test.Comment;

import com.test.test.Article.Article;
import com.test.test.Comment.Dto.CommentRequestDto;
import com.test.test.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "articleId")
    private Article article;

    public void updateComment(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
