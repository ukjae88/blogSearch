package com.my.blogsearch.application.api;

import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import com.my.blogsearch.application.blog.dto.BlogContentDto;

import java.util.List;

public interface BlogSearchApi {
    List<BlogContentDto> searchBlog(BlogSearchRequestDto blogSearchRequestDto);
}
