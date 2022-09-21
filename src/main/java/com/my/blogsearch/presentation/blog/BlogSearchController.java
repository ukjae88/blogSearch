package com.my.blogsearch.presentation.blog;

import com.my.blogsearch.application.blog.service.BlogSearchService;
import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import com.my.blogsearch.application.common.ApiResponse;
import com.my.blogsearch.application.common.aspect.BlogSearchRequestCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogSearchController {
    private final BlogSearchService blogSearchService;

    @GetMapping("/blogs")
    @BlogSearchRequestCheck
    public ApiResponse getBlogs(@ModelAttribute BlogSearchRequestDto blogSearchRequestDto) {
        return ApiResponse.success(blogSearchService.getBlogs(blogSearchRequestDto));
    }

    @GetMapping("/keywords")
    public ApiResponse getTopKeywords() {
        return ApiResponse.success(blogSearchService.getTopKeywords());
    }
}
