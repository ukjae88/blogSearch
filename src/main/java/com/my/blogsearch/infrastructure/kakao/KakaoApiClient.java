package com.my.blogsearch.infrastructure.kakao;

import com.my.blogsearch.infrastructure.kakao.Response.KaKaoBlogResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoApiClient {
    private final WebClient webClient;

    @Value("${kakao.rest.api.key}")
    private String KAKAO_REST_API_KEY;

    public KakaoApiClient(@Value("${kakao.api}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<KaKaoBlogResponse> searchKakaoBlog(String query, String sort, Integer page, Integer size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v2/search/blog")
                        .queryParam("query", query)
                        .queryParam("sort", sort)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY)
                .retrieve()
                .bodyToMono(KaKaoBlogResponse.class);
    }
}
