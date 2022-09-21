package com.my.blogsearch.infrastructure.kakao;

import com.my.blogsearch.application.api.BlogSearchApi;
import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import com.my.blogsearch.application.blog.dto.BlogContentDto;
import com.my.blogsearch.infrastructure.kakao.Response.KaKaoBlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoApiAdapter implements BlogSearchApi {
    private final KakaoApiClient kakaoApiClient;

    @Override
    public List<BlogContentDto> searchBlog(BlogSearchRequestDto blogSearchRequestDto) {
        KaKaoBlogResponse response = kakaoApiClient.searchKakaoBlog(blogSearchRequestDto.getQuery(), blogSearchRequestDto.getSort(), blogSearchRequestDto.getPage(), blogSearchRequestDto.getSize()).block();
        if (response == null)
            return null;

        List<BlogContentDto> blogs = new ArrayList<>();
        for (KaKaoBlogResponse.BlogDocument blog : response.getDocuments()) {
            blogs.add(BlogContentDto.of(blog.getTitle(), blog.getContents(), blog.getUrl(), blog.getDatetime()));
        }

        return blogs;
    }
}
