package com.my.blogsearch.application.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogKeywordDto {
    private String keyword;
    private Long count;

    public static BlogKeywordDto of(String keyword, Long count) {
        return BlogKeywordDto.builder()
                .keyword(keyword)
                .count(count)
                .build();
    }
}
