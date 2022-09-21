package com.my.blogsearch.application.blog.service;

import com.my.blogsearch.application.api.BlogSearchApi;
import com.my.blogsearch.application.blog.dto.BlogContentDto;
import com.my.blogsearch.application.blog.dto.BlogKeywordDto;
import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import com.my.blogsearch.domain.blog.BlogKeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

/**
 * Blog Search 서비스 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class BlogSearchServiceTest {

    @Mock
    private BlogKeywordRepository blogKeywordRepository;

    @Mock
    private BlogSearchApi blogSearchApi;

    @InjectMocks
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
        given(blogSearchApi.searchBlog(any())).willReturn(List.of(BlogContentDto.of("블로그 제목", "블로그 내용", "블로그 URL", LocalDateTime.now())));

        // when
        List<BlogContentDto> result = blogSearchService.getBlogs(blogSearchRequestDto);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTitle()).isEqualTo("블로그 제목");
    }

    @DisplayName("2. 블로그 검색 키워드 카운트 증가")
    @Order(2)
    @Test
    void addKeywordCount(){
        // given
        given(blogKeywordRepository.addCount(any())).willReturn(1L);

        // when
        Long result = blogSearchService.addKeywordCount(blogSearchRequestDto.getQuery());

        // then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("3. 블로그 인기 검색 키워드 조회")
    @Order(3)
    @Test
    void getTopKeywords(){
        // given
        given(blogKeywordRepository.findTopKeywords(any())).willReturn(List.of(BlogKeywordDto.of(blogSearchRequestDto.getQuery(), 1L)));

        // when
        List<BlogKeywordDto> result = blogSearchService.getTopKeywords();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getKeyword()).isEqualTo(blogSearchRequestDto.getQuery());
        assertThat(result.get(0).getCount()).isEqualTo(1L);
    }
}