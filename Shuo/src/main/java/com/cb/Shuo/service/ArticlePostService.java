package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.model.entity.ArticleModel;
import com.cb.Shuo.model.json.ArticleJson;
import com.cb.Shuo.service.util.GetCurrentLocalDateTime;
import com.cb.Shuo.service.util.IdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  public String postArticle(ArticleJson articleJson, String userId) {
    String articleId = IdGenerator.generateArticleId();
    new Thread(
            () -> {
              ArticleModel articleModel = new ArticleModel();
              articleModel.setArticleId(IdGenerator.generateArticleId());
              articleModel.setCreateTime(GetCurrentLocalDateTime.getCurrentTime());
              logger.debug(articleModel.getCreateTime().toString());
              articleModel.setTitle(articleJson.getTitle());
              articleModel.setUserId(userId);
              articleModel.setContent(articleJson.getContent());

              if (articleJson.getImages() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                  articleModel.setImages(objectMapper.writeValueAsString(articleJson.getImages()));
                } catch (JsonProcessingException e) {
                  e.printStackTrace();
                }
              }
              logger.debug("postArticle " + articleModel.getArticleId());
              articleDao.save(articleModel);
            })
        .start();
    return articleId;
  }

  public void viewArticle(String articleId) {
    logger.debug("viewArticle " + articleId);
    ArticleModel articleModel = articleDao.findArticleModelByArticleId(articleId);
    articleModel.setViews(articleModel.getViews() + 1);
    articleDao.save(articleModel);
  }
}
