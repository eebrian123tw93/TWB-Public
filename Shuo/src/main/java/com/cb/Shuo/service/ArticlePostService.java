package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.model.json.ArticleJson;
import com.cb.Shuo.service.util.IdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticlePostService {
  private static final Logger logger = LoggerFactory.getLogger(ArticlePostService.class);

  private final ArticleDao articleDao;

  @Autowired
  public ArticlePostService(ArticleDao articleDao) {
    this.articleDao = articleDao;
  }

  public void postArticle(ArticleJson articleJson) {
    ArticleModel articleModel = new ArticleModel();
    articleModel.setArticleId(IdGenerator.generateArticleId());
    articleModel.setCreateTime(LocalDateTime.now().withNano(0));
    articleModel.setTitle(articleJson.getTitle());
    articleModel.setUserId(articleJson.getUserId());
    articleModel.setContent(articleJson.getContent());

    if (articleJson.getImages() != null) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        articleModel.setImages(objectMapper.writeValueAsString(articleJson.getImages()));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    logger.info("postArticle " + articleModel.getArticleId());
    articleDao.save(articleModel);
  }
}