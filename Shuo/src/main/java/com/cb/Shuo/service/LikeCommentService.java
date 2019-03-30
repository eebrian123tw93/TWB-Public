package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.dao.CommentDao;
import com.cb.Shuo.dao.LikeDao;
import com.cb.Shuo.model.entity.ArticleModel;
import com.cb.Shuo.model.entity.CommentModel;
import com.cb.Shuo.model.entity.LikeModel;
import com.cb.Shuo.model.json.CommentJson;
import com.cb.Shuo.model.json.LikeJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LikeCommentService {

  private final LikeDao likeDao;
  private final CommentDao commentDao;
  private final ArticleDao articleDao;

  @Autowired
  public LikeCommentService(LikeDao likeDao, CommentDao commentDao, ArticleDao articleDao) {
    this.likeDao = likeDao;
    this.commentDao = commentDao;
    this.articleDao = articleDao;
  }

  public int like(LikeJson likeJson, String userId) {
    ArticleModel articleModel = articleDao.findArticleModelByArticleId(likeJson.getArticleId());

    // check if article exists
    if (articleModel != null) {
      LikeModel likeModel = new LikeModel();
      likeModel.setArticleId(likeJson.getArticleId());
      likeModel.setType(likeJson.getType());
      likeModel.setUserId(userId);

      // check if user has already liked this article before
      LikeModel l = likeDao.findByUserIdAndArticleId(likeJson.getArticleId(), userId);

      // check if user is new to up or down voting article
      if (l == null) {
        log.info("new like of type " + likeModel.getType());
        articleModel.setPoints(articleModel.getPoints() + likeModel.getType());
        // save like history
        likeDao.save(likeModel);
      }

      // if user is removing up vote or down vote
      else if (l.getType() == likeModel.getType()) {
        log.info("remove like of type " + l.getType());
        articleModel.setPoints(articleModel.getPoints() - l.getType());
        likeDao.delete(l);
      }

      // if user is changing up to down or vice versa
      else {
        log.info("change like of type " + l.getType() + " to " + likeModel.getType());
        articleModel.setPoints(articleModel.getPoints() + likeModel.getType() * 2);
        likeDao.delete(l);
        likeDao.save(likeModel);
      }

      articleDao.save(articleModel);
      return 0;
    } else return 1;
  }

  public int comment(CommentJson commentJson, String userId) {

    CommentModel commentModel = new CommentModel();

    commentModel.setArticleId(commentJson.getArticleId());
    commentModel.setComment(commentJson.getComment());
    commentModel.setCreateTime(LocalDateTime.now().withNano(0));
    commentModel.setUserId(userId);

    ArticleModel articleModel = articleDao.findArticleModelByArticleId(commentModel.getArticleId());

    if (articleModel != null) {
      commentDao.save(commentModel);
      articleModel.setCommentCount(articleModel.getCommentCount() + 1);
      articleDao.save(articleModel);
      return 0;
    } else return 1;
  }

  public List<CommentJson> getComments(String articleId) {
    List<CommentModel> commentModelList = commentDao.findCommentModelsByArticleId(articleId);

    if (commentModelList != null && commentModelList.size() > 0) {

      List<CommentJson> commentJsonList = new ArrayList<>();

      commentModelList.forEach(
          commentModel -> {
            CommentJson commentJson = new CommentJson();
            commentJson.setUserId(commentModel.getUserId());
            commentJson.setComment(commentModel.getComment());
            commentJson.setArticleId(commentModel.getArticleId());
            commentJson.setCommentTime(commentModel.getCreateTime());
            commentJsonList.add(commentJson);
          });
      return commentJsonList;
    } else return new ArrayList<>();
  }

  //  public boolean checkLiked(String articleId, String userId) {}
}
