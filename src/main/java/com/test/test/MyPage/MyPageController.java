package com.test.test.MyPage;


import com.test.test.Member.Member;
import com.test.test.Login.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/myPage/article")
    public ResponseEntity<?> myArticle(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(myPageService.myArticle(member));
    }

    @GetMapping("/myPage/comment")
    public ResponseEntity<?> myComment(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(myPageService.myComment(member));
    }

    @GetMapping("/myPage/subComment")
    public ResponseEntity<?> mySubComment(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(myPageService.mySubComment(member));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> myProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok().body(myPageService.myProfile(member));
    }
}
