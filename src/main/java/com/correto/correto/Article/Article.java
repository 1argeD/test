package com.correto.correto.Article;

import com.correto.correto.Article.Dto.ArticleRequestDto;
import com.correto.correto.Attachment.Attachment;
import com.correto.correto.Board.Board;
import com.correto.correto.Timestamped;
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

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

public void update(ArticleRequestDto articleRequestDto) {
    this.title = articleRequestDto.getTitle();
    this.content = articleRequestDto.getContent();
}

public void view() {
    this.viewCount = viewCount + 1;
}
}
