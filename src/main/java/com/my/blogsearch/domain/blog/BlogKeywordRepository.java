package com.my.blogsearch.domain.blog;

import org.springframework.data.repository.CrudRepository;

public interface BlogKeywordRepository extends CrudRepository<BlogKeyword, String>, BlogKeywordRepositoryCustom {
}
