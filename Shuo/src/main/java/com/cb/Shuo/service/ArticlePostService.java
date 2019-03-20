package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.service.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticlePostService {
  private static final Logger logger = LoggerFactory.getLogger(ArticlePostService.class);

  private final ArticleDao articleDao;

  @Autowired
  public ArticlePostService(ArticleDao articleDao) {
    this.articleDao = articleDao;
  }

  public void postArticle(ArticleModel articleModel) {
    logger.info("postArticle");
    articleModel.setArticleId(IdGenerator.generateArticleId());
    // todo: fix date time problem
    articleDao.save(articleModel);
  }
}
