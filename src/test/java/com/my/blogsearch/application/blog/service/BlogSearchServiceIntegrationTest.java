package com.my.blogsearch.application.blog.service;

import com.my.blogsearch.application.blog.dto.BlogContentDto;
import com.my.blogsearch.application.blog.dto.BlogKeywordDto;
import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Blog Search 서비스 통합 테스트
 */
@SpringBootTest
class BlogSearchServiceIntegrationTest {

    @Autowired
    private BlogSearchService blogSearchService;

    private BlogSearchRequestDto blogSearchRequestDto;

    @BeforeEach
    void setUp() {
        String query = "검색 키워드";
        String sort = "accuracy";
        Integer page = 1;
        Integer size = 10;
        blogSearchRequestDto = new BlogSearchRequestDto(query, sort, page, size);
    }

    @DisplayName("1. 블로그 검색")
    @Order(1)
    @Test
    void getBlogs(){
        // given

        // when
        List<BlogContentDto> result = blogSearchService.getBlogs(blogSearchRequestDto);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.get(0).getTitle()).isNotEmpty();
    }


    @DisplayName("2. 블로그 인기 검색 키워드 조회")
    @Order(2)
    @Test
    void addKeywordCount(){
        // given
        blogSearchService.getBlogs(blogSearchRequestDto);

        // when
        List<BlogKeywordDto> result = blogSearchService.getTopKeywords();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getKeyword()).isEqualTo(blogSearchRequestDto.getQuery());
        assertThat(result.get(0).getCount()).isEqualTo(1);
    }

    @DisplayName("2-1. 블로그 인기 검색 키워드 조회 - 동시성 테스트")
    @Order(3)
    @Test
    @Disabled
    void addKeywordCountByThreads() throws InterruptedException {
        // given
        blogSearchRequestDto.setQuery("카카오뱅크");
        
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                blogSearchService.getBlogs(blogSearchRequestDto);
                latch.countDown();
            });
        }
        latch.await();

        // when
        List<BlogKeywordDto> result = blogSearchService.getTopKeywords();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCount()).isEqualTo(10);
    }
}