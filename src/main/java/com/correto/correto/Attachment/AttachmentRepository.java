package com.correto.correto.Attachment;

import com.correto.correto.Article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
}
