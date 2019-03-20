package com.cb.Shuo.dao;

import com.cb.Shuo.model.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDao extends JpaRepository<ArticleModel, Long> {
}
