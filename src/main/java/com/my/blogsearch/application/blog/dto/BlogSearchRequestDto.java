package com.my.blogsearch.application.blog.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class BlogSearchRequestDto {
    private String query;
    private String sort;
    private Integer page;
    private Integer size;
}
