package com.cb.Shuo.dao;

import com.cb.Shuo.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<CommentModel, Long> {
  List<CommentModel> findCommentModelsByArticleId(String articleId);
}
