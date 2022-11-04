package com.test.test.SubComment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment>findAllByComment_Id(Long commentId);

    List<SubComment> findAllByMember_Id(Long memberId);
}
