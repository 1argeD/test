package com.test.test.Member;

import com.test.test.Article.Article;
import com.test.test.Comment.Comment;
import com.test.test.SubComment.SubComment;
import com.test.test.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String role;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Article> articles;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> Comments;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SubComment> subComments;

    public Member(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        role = "ROLE_USER";
    }
}
