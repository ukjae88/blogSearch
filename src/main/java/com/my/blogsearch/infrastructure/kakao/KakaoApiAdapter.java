package com.my.blogsearch.infrastructure.kakao;

import com.my.blogsearch.application.api.BlogSearchApi;
import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import com.my.blogsearch.application.blog.dto.BlogContentDto;
import com.my.blogsearch.infrastructure.kakao.Response.KaKaoBlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KakaoApiAdapter implements BlogSearchApi {
    private final KakaoApiClient kakaoApiClient;

    @Override
    public List<BlogContentDto> searchBlog(BlogSearchRequestDto blogSearchRequestDto) {
        KaKaoBlogResponse response = kakaoApiClient.searchKakaoBlog(blogSearchRequestDto.getQuery(), blogSearchRequestDto.getSort(), blogSearchRequestDto.getPage(), blogSearchRequestDto.getSize()).block();
        if (response != null && response.isSuccess()) {
            return response.getDocuments().stream()
                    .map(it -> BlogContentDto.of(it.getTitle(), it.getContents(), it.getUrl(), it.getDatetime()))
                    .collect(Collectors.toList());
        }

        return null;
    }
}
