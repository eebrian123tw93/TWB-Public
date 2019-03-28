package com.cb.Shuo.dao;

import com.cb.Shuo.model.LikeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeDao extends JpaRepository<LikeModel, Long> {

  @Query(
      nativeQuery = true,
      value = "select * from likes where article_id like :articleId and user_id like :userId")
  LikeModel findByUserIdAndArticleId(
      @Param("articleId") String articleId, @Param("userId") String userId);

  List<LikeModel> findAllByUserId(String userId);
}
