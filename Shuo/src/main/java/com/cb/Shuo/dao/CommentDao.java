package com.cb.Shuo.dao;

import com.cb.Shuo.model.entity.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<CommentModel, Long> {
  List<CommentModel> findCommentModelsByArticleId(String articleId);
  List<CommentModel> findCommentModelsByUserId(String userId);
}
