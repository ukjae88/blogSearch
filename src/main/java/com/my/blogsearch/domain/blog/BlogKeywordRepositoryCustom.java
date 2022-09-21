package com.my.blogsearch.domain.blog;

import com.my.blogsearch.application.blog.dto.BlogKeywordDto;

import java.util.List;

public interface BlogKeywordRepositoryCustom {
    Long addCount(String keyword);
    List<BlogKeywordDto> findTopKeywords(Integer count);
}
