package com.cb.Shuo.dao;

import com.cb.Shuo.model.entity.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<ArticleModel, Long> {
  ArticleModel findArticleModelByArticleId(String articleId);

  @Query(
      nativeQuery = true,
      value =
          "select * from articles where create_time > :startDate and create_time < :endDate order by points desc limit :limitNum offset :offsetNum")
  List<ArticleModel> getArticleModelsOrderByPoints(
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("limitNum") int limitNum,
      @Param("offsetNum") int offsetNum);

  @Query(
      nativeQuery = true,
      value =
          "select * from articles where create_time > :startDate and create_time < :endDate order by create_time desc limit :limitNum offset :offsetNum")
  List<ArticleModel> getArticleModelsOrderByCreateTime(
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("limitNum") int limitNum,
      @Param("offsetNum") int offsetNum);

  List<ArticleModel> getArticleModelsByUserIdOrderByCreateTimeDesc(String userId);
}
