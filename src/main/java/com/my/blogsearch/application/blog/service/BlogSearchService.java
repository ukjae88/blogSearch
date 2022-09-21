package com.my.blogsearch.application.blog.service;

import com.my.blogsearch.application.api.BlogSearchApi;
import com.my.blogsearch.application.blog.dto.BlogContentDto;
import com.my.blogsearch.application.blog.dto.BlogKeywordDto;
import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import com.my.blogsearch.domain.blog.BlogKeyword;
import com.my.blogsearch.domain.blog.BlogKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogSearchService {
    private final BlogSearchApi blogSearchApi;
    private final BlogKeywordRepository blogKeywordRepository;

    public List<BlogContentDto> getBlogs(BlogSearchRequestDto blogSearchRequestDto) {
        addKeywordCount(blogSearchRequestDto.getQuery());
        return blogSearchApi.searchBlog(blogSearchRequestDto);
    }

    @Transactional
    public Long addKeywordCount(String keyword) {
        saveKeywordCount(keyword);
        return blogKeywordRepository.addCount(keyword);
    }

    private void saveKeywordCount(String keyword) {
        if (!blogKeywordRepository.existsById(keyword))
            blogKeywordRepository.save(BlogKeyword.of(keyword));
    }

    public List<BlogKeywordDto> getTopKeywords() {
        final int limit = 10;
        return blogKeywordRepository.findTopKeywords(limit);
    }
}
