package com.hodolog.hodollog.repository;

import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.dto.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
