package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.dao.CommentDao;
import com.cb.Shuo.dao.LikeDao;
import com.cb.Shuo.model.entity.ArticleModel;
import com.cb.Shuo.model.entity.CommentModel;
import com.cb.Shuo.model.entity.LikeModel;
import com.cb.Shuo.model.json.ArticleDataJson;
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
import java.util.List;

@Service
@Slf4j
public class ArticleGetService {

  private final ArticleDao articleDao;
  private final LikeDao likeDao;
  private final CommentDao commentDao;

  @Autowired
  public ArticleGetService(ArticleDao articleDao, LikeDao likeDao, CommentDao commentDao) {
    this.articleDao = articleDao;
    this.likeDao = likeDao;
    this.commentDao = commentDao;
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
    List<ArticleModel> articleModelList;

    switch (orderBy) {
      case "points":
        articleModelList = articleDao.getArticleModelsOrderByPoints(start, end, limit, offset);
        break;
      case "create_time":
        articleModelList = articleDao.getArticleModelsOrderByCreateTime(start, end, limit, offset);
        break;
      default:
        log.error("unknown orderBy: " + orderBy);
        return new ArrayList<>();
    }

    log.info("articleModelList.size(): " + articleModelList.size());
    List<ArticleJson> articleJsonList = convertModelToJson(articleModelList);

    if (userId != null) {
      articleJsonList.forEach(
          articleJson -> {
            LikeModel likeModel =
                likeDao.findByUserIdAndArticleId(articleJson.getArticleId(), userId);
            if (likeModel != null) articleJson.setLikeStatus(likeModel.getType());
          });
    }

    return articleJsonList;
  }

  public ArticleDataJson getArticleData(String articleId, String userId) {
    ArticleModel articleModel = articleDao.findArticleModelByArticleId(articleId);
    List<CommentModel> commentModelList = commentDao.findCommentModelsByArticleId(articleId);

    ArticleDataJson articleDataJson = new ArticleDataJson();
    articleDataJson.setPoints(articleModel.getPoints());
    articleDataJson.setViews(articleModel.getViews());
    articleDataJson.setCommentCount(articleModel.getCommentCount());

    List<CommentJson> commentJsonList = new ArrayList<>();

    commentModelList.forEach(
        commentModel -> {
          CommentJson commentJson = new CommentJson();
          commentJson.setArticleId(commentModel.getArticleId());
          commentJson.setUserId(commentModel.getUserId());
          commentJson.setCommentTime(commentModel.getCreateTime());
          commentJson.setComment(commentModel.getComment());
          commentJsonList.add(commentJson);
        });

    articleDataJson.setComments(commentJsonList);

    if (userId != null) {
      LikeModel likeModel = likeDao.findByUserIdAndArticleId(articleId, userId);
      if (likeModel != null) articleDataJson.setLikeStatus(likeModel.getType());
    }

    return articleDataJson;
  }

  public List<ArticleJson> getArticlesByAuthor(String userId) {
    return convertModelToJson(articleDao.getArticleModelsByUserIdOrderByCreateTimeDesc(userId));
  }

  public List<ArticleJson> searchArticles(String keyWord, int limit, int offset) {
    log.info("search keyword " + keyWord);
    return convertModelToJson(articleDao.searchArticle("%" + keyWord + "%", limit, offset));
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
