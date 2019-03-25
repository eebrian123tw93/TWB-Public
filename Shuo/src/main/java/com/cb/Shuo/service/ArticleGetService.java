package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.model.json.ArticleJson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

  public List<ArticleJson> getAll() {
    return convertModelToJson(articleDao.findAll());
  }

  public List<ArticleJson> publicGet(
      LocalDateTime start, LocalDateTime end, Integer limit, Integer offset) {
    List<ArticleModel> articleModelList = articleDao.getNewestArticles(limit);
    return convertModelToJson(articleModelList);
  }

  private List<ArticleJson> convertModelToJson(List<ArticleModel> articleModelList) {
    List<ArticleJson> articleJsonList = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    articleModelList.forEach(
        articleModel -> {
          ArticleJson articleJson = new ArticleJson();

          articleJson.setArticleId(articleModel.getArticleId());
          articleJson.setUserId(articleModel.getUserId());
          articleJson.setTitle(articleModel.getTitle());
          articleJson.setContent(articleModel.getContent());

          if (articleModel.getImages() != null) {
            try {
              articleJson.setImages(
                  objectMapper.readValue(
                      articleModel.getImages(), new TypeReference<List<String>>() {}));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }

          articleJson.setCreateTime(articleModel.getCreateTime());
          articleJson.setCommentCount(articleModel.getCommentCount());
          articleJson.setPoints(articleModel.getPoints());
          articleJson.setViews(articleModel.getViews());
          articleJsonList.add(articleJson);
        });

    return articleJsonList;
  }
}
