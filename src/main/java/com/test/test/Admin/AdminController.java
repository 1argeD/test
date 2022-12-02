package com.test.test.Admin;

import com.test.test.Login.UserDetailsImpl;
import com.test.test.Member.Member;
import com.test.test.Member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class AdminController {
    private AdminService adminService;

    @GetMapping(value = "/admin/memberList")
    ResponseEntity<?> memberList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member admin = userDetails.getMember();
        List<MemberResponseDto> response = adminService.memberList(admin);
        return ResponseEntity.ok().body(response);
    };


    /* 게시글 전체 삭제 */
    @DeleteMapping(value="admin/deleteAll")
    ResponseEntity<?> deleteAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member admin = userDetails.getMember();
        adminService.deleteAll(admin);
        return ResponseEntity.ok().body(Map.of("msg","모든 게시물을 삭제합니다."));
    }
}
