package com.correto.correto.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAllByTitleContaining(String title);
    List<Article> findAllByBoard_NameContaining(String boardName);

    @Query("select a from Article a " +
            "inner join Board b on b.id = a.board.id " +
            "where b.name like %:boardName% " )
    List<Article>getArticleByBoard_Name(String boardName);

}
