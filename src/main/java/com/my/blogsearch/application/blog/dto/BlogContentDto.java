package com.my.blogsearch.application.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogContentDto {
    private String title;
    private String contents;
    private String url;
    private LocalDateTime datetime;

    public static BlogContentDto of(String title, String contents, String url, LocalDateTime datetime) {
        return BlogContentDto.builder()
                .title(title)
                .contents(contents)
                .url(url)
                .datetime(datetime)
                .build();
    }
}
