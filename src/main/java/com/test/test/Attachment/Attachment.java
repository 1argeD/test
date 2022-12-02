package com.test.test.Attachment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.test.Article.Article;
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
public class Attachment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "articleId")
    private Article article;
}
