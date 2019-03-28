package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.dao.CommentDao;
import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.model.CommentModel;
import com.cb.Shuo.model.json.ArticleJson;
import com.cb.Shuo.model.json.CommentJson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ArticleGetService {

  private final ArticleDao articleDao;

  @Autowired
  public ArticleGetService(ArticleDao articleDao) {
    this.articleDao = articleDao;
  }

  public List<ArticleJson> getAll() {
    return convertModelToJson(articleDao.findAll());
  }

  public List<ArticleJson> getArticles(
      LocalDateTime start,
      LocalDateTime end,
      Integer limit,
      Integer offset,
      String userId,
      String orderBy) {
    log.info("getArticles");
    List<ArticleModel> articleModelList = articleDao.getArticles(limit, start, end, offset);
    log.info("articleModelList.size(): " + articleModelList.size());

    List<ArticleJson> articleJsonList = convertModelToJson(articleModelList);

    if (orderBy.equals("likes")) {
      articleJsonList.sort(Comparator.comparingInt(ArticleJson::getPoints));
      Collections.reverse(articleJsonList);
    } else if (orderBy.equals("time")) {
      articleJsonList.sort(Comparator.comparing(ArticleJson::getCreateTime));
    }

    return articleJsonList;
  }

  private List<ArticleJson> convertModelToJson(List<ArticleModel> articleModelList) {
    log.info("convertModelToJson");
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
    log.info("articleJson size " + articleJsonList.size());
    return articleJsonList;
  }
}
