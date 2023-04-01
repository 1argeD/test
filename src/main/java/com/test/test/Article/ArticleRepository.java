package com.test.test.Article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAllByTitleContaining(String title);

    List<Article>findArticleByBoard_Name(String boardName);

    List<Article>findAllByMember_Id(Long memberId);

}
