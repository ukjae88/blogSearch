package com.my.blogsearch.application.common.aspect;

import com.my.blogsearch.application.blog.dto.BlogSearchRequestDto;
import com.my.blogsearch.application.common.exception.InvalidParameterException;
import com.my.blogsearch.application.common.exception.RequiredKeywordException;
import io.netty.util.internal.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BlogSearchRequestCheckAspect {

    @Around(value = "@annotation(blogRequestCheck) && args(requestDto, ..)")
    public Object checkBlogSearchRequest(ProceedingJoinPoint joinPoint, BlogSearchRequestCheck blogRequestCheck, BlogSearchRequestDto requestDto) throws Throwable {
        String keyword = requestDto.getQuery();
        if (StringUtil.isNullOrEmpty(keyword))
            throw new RequiredKeywordException();

        Integer page = requestDto.getPage();
        Integer size = requestDto.getSize();
        if (verifyPageOrSize(page) || verifyPageOrSize(size))
            throw new InvalidParameterException();

        return joinPoint.proceed();
    }

    private boolean verifyPageOrSize(Integer value) {
        if (value == null)
            return false;

        return value < 1 || value > 50;
    }
}
