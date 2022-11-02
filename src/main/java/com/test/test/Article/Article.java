package com.test.test.Article;

import com.test.test.Article.Dto.ArticleRequestDto;
import com.test.test.Attachment.Attachment;
import com.test.test.Board.Board;
import com.test.test.Comment.Comment;
import com.test.test.Member.Member;
import com.test.test.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String content;

    private int viewCount;

    private int likeCnt;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

public void update(ArticleRequestDto articleRequestDto) {
    this.title = articleRequestDto.getTitle();
    this.content = articleRequestDto.getContent();
}

public void view() {
    this.viewCount = viewCount + 1;
}

public void like() {this.likeCnt = likeCnt + 1;}
}
