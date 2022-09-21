package com.my.blogsearch.domain.blog;

import com.my.blogsearch.application.blog.dto.BlogKeywordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogKeywordRepositoryImpl implements BlogKeywordRepositoryCustom {
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Integer> hashOperations;
    private SetOperations<String, Object> setOperations;

    private static final String REDIS_SET = "keywords";
    private static final String REDIS_HASH = "blog";
    private static final String REDIS_HASH_SEPARATOR = ":";
    private static final String HASH_KEY = "count";

    @PostConstruct
    public void postConstruct() {
        hashOperations = redisTemplate.opsForHash();
        setOperations = redisTemplate.opsForSet();
    }

    @Override
    public Long addCount(String keyword) {
        final int delta = 1;
        setOperations.add(REDIS_SET, keyword);
        return hashOperations.increment(REDIS_HASH + REDIS_HASH_SEPARATOR + keyword, HASH_KEY, delta);
    }

    @Override
    public List<BlogKeywordDto> findTopKeywords(Integer limit) {
        final String by = "blog:*->count";
        final String getKeyword = "blog:*->keyword";
        final String getCount = "blog:*->count";
        SortQuery<String> sortQuery = SortQueryBuilder.sort(REDIS_SET).by(by).limit(0, limit).get(getKeyword).get(getCount).order(SortParameters.Order.DESC).build();
        List<Object> sortedData = redisTemplate.sort(sortQuery);
        return getSortedKeywords(sortedData);
    }

    private List<BlogKeywordDto> getSortedKeywords(List<Object> sortedData) {
        if (CollectionUtils.isEmpty(sortedData) || sortedData.get(0) == null)
            return null;

        List<BlogKeywordDto> blogKeywords = new ArrayList<>();
        String keyword = null;
        for (int i = 0; i < sortedData.size(); i++) {
            if (i % 2 == 0) {
                keyword = (String)sortedData.get(i);
            } else {
                long count = Long.parseLong((String)sortedData.get(i));
                blogKeywords.add(BlogKeywordDto.of(keyword, count));
            }
        }
        return blogKeywords;
    }
}
