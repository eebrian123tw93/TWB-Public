package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.model.json.ArticleJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleGetService {

  private static final Logger logger = LoggerFactory.getLogger(ArticleGetService.class);

  private final ArticleDao articleDao;

  @Autowired
  public ArticleGetService(ArticleDao articleDao) {
    this.articleDao = articleDao;
  }

  public List<ArticleModel> getByDateRange(LocalDateTime start, LocalDateTime end) {
    return new ArrayList<>();
  }

  public List<ArticleModel> getAll() {
    return articleDao.findAll();
  }

  public List<ArticleJson> publicGet(
      LocalDateTime start, LocalDateTime end, int limit, int offset) {
    return new ArrayList<>();
  }
}
