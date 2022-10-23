package com.hodolog.hodollog.repository;

import com.hodolog.hodollog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
