package com.my.blogsearch.infrastructure.kakao.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KaKaoBlogResponse {
    List<BlogDocument> documents;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BlogDocument {
        String title;
        String contents;
        String url;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        LocalDateTime datetime;
    }
}
