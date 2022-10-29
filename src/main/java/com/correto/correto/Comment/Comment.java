package com.correto.correto.Comment;

import com.correto.correto.Article.Article;
import com.correto.correto.Comment.Dto.CommentRequestDto;
import com.correto.correto.Timestamped;
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
