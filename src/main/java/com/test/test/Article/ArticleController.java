package com.test.test.Article;

import com.test.test.Article.Dto.ArticleRequestDto;
import com.test.test.Article.Dto.ArticleResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    /*게시글 생성하기 */
    @PostMapping("/article/{boardId}")
    public ResponseEntity<?> createPost(@PathVariable Long boardId,
                                        @RequestBody ArticleRequestDto requestDto) {
        return ResponseEntity.ok().body(articleService.createPost(boardId, requestDto));
    }

    /*제목으로 게시글 찾기*/
    @GetMapping("/articles/title")
    ResponseEntity<?> searchTitle(String title, Model model) {
        List<ArticleResponseDto> searchList = articleService.searchTitle(title);
        model.addAttribute("searchList", searchList);
        return ResponseEntity.ok().body(articleService.searchTitle(title));
    }

    /*게시판 이름으로 게시글 검색하기*/
    @GetMapping("/articles/name")
    ResponseEntity<?> searchName(String boardName, Model model) {
        List<ArticleResponseDto> nameList = articleService.searchName(boardName);
        model.addAttribute("nameList", nameList);
        return ResponseEntity.ok().body(articleService.searchName(boardName));
    }

    /*게시글 수정하기 */
    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDto requestDto) {
        return ResponseEntity.ok().body(articleService.updateArticle(articleId, requestDto));
    }

    /*게시글 삭제*/
    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok().body(Map.of("msg", "삭제 완료"));
    }

    /*날짜로 검색하기*/
    @GetMapping("/article/date")
    public ResponseEntity<?> searchDate(@RequestParam(value = "searchStartDate", required = false) String searchStartDate,
                                        @RequestParam(value = "searchEndDate", required = false) String searchEndDate) {
        return ResponseEntity.ok().body(articleService.searchDate(searchStartDate, searchEndDate));
    }

    /*게시물 상세 조회*/
    @GetMapping("/view/{articleId}")
    public ResponseEntity<?> view(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(articleService.view(articleId));
    }

    /*좋아요(추천) 기능*/
    @PostMapping("/articleLike/{articleId}")
    private ResponseEntity<?> articleLike(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(articleService.articleLike(articleId));
    }
}
