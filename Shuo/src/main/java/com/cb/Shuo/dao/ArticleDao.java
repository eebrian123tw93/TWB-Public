package com.cb.Shuo.dao;

import com.cb.Shuo.model.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<ArticleModel, Long> {
  ArticleModel findArticleModelByArticleId(String articleId);

  @Query(
      nativeQuery = true,
      value = "select * from articles order by create_time desc limit :limitNum")
  List<ArticleModel> getNewestArticles(@Param("limitNum") int limitNum);
}
