package com.correto.correto.Article;

import com.correto.correto.Article.Dto.ArticleResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDateTime;
import java.util.List;

import static com.correto.correto.Article.QArticle.article;

@Slf4j
@Repository
public class ArticleQueryRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ArticleQueryRepository(JPAQueryFactory factory) {
        super(Article.class);
        this.queryFactory = factory;
    }

    @InitBinder
    public List<ArticleResponseDto> filter(LocalDateTime searchStartDate, LocalDateTime searchEndDate) {

        JPQLQuery<ArticleResponseDto> result = queryFactory
                .select(Projections.fields(ArticleResponseDto.class,
                        article.id, article.title, article.content, article.created_datetime))
                .from(article)
                /*생성날짜 기준으로 시작날짜와 마지막날짜 사이의 데이터 조건*/
                .where(article.created_datetime.between(searchStartDate, searchEndDate))
                .orderBy(article.id.desc());
//        long totalCnt = result.fetchCount();
//        List<ArticleResponseDto> list = getQuerydsl().applyPagination(pageable, result).fetch();
//        return new PageImpl<>(list, pageable, totalCnt);
        return result.fetch();
    }
}
