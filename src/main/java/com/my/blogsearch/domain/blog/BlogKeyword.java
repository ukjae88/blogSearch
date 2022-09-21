package com.my.blogsearch.domain.blog;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash("blog")
public class BlogKeyword {
    @Id
    private String keyword;
    private Long count;

    public static BlogKeyword of(String keyword) {
        return BlogKeyword.builder()
                .keyword(keyword)
                .count(0L)
                .build();
    }
}
