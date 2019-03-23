package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.dao.CommentDao;
import com.cb.Shuo.dao.LikeDao;
import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.model.CommentModel;
import com.cb.Shuo.model.LikeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeCommentService {
  private static final Logger logger = LoggerFactory.getLogger(LikeCommentService.class);

  private final LikeDao likeDao;
  private final CommentDao commentDao;
  private final ArticleDao articleDao;

  @Autowired
  public LikeCommentService(LikeDao likeDao, CommentDao commentDao, ArticleDao articleDao) {
    this.likeDao = likeDao;
    this.commentDao = commentDao;
    this.articleDao = articleDao;
  }

  public int like(LikeModel likeModel) {
    LikeModel l = likeDao.findByUserIdAndArticleId(likeModel.getArticleId(), likeModel.getUserId());
    ArticleModel articleModel = articleDao.findArticleModelByArticleId(likeModel.getArticleId());

    // check if article exists
    if (articleModel != null) {
      // check if user is new to up or down voting article
      if (l == null) {
        articleModel.setPoints(articleModel.getPoints() + likeModel.getType());
        // save like history
        likeDao.save(likeModel);
      }

      // if user is removing up vote or down vote
      else if (l.getType() == likeModel.getType()) {
        articleModel.setPoints(articleModel.getPoints() - likeModel.getType());
        likeDao.delete(likeModel);
      }

      // if user is changing up to down or vice versa
      else {
        articleModel.setPoints(articleModel.getPoints() + likeModel.getType() * 2);
        likeDao.delete(l);
        likeDao.save(likeModel);
      }

      articleDao.save(articleModel);
      return 0;
    } else return 1;
  }

  public int addLike(LikeModel likeModel) {
    //    ArticleModel articleModel =
    // articleDao.findArticleModelByArticleId(likeModel.getArticleId());
    //
    //    if (articleModel != null) {
    //      articleModel.setPoints(articleModel.getPoints() + likeModel.getType());
    //      articleDao.save(articleModel);
    //      // save like history
    //      likeDao.save(likeModel);
    //      return 0;
    //    } else return 1;
    return 0;
  }

  public int removeLike(LikeModel likeModel) {
    ArticleModel articleModel = articleDao.findArticleModelByArticleId(likeModel.getArticleId());

    try {
      articleModel.setPoints(articleModel.getPoints() - likeModel.getType());
      articleDao.save(articleModel);
      likeDao.delete(likeModel);
    } catch (IllegalArgumentException e) {
      logger.info(e.getMessage());
      return 1;
    }
    return 0;
  }

  public int addComment(CommentModel commentModel) {
    if (articleDao.findArticleModelByArticleId(commentModel.getArticleId()) != null) {
      commentDao.save(commentModel);
      return 0;
    } else return 1;
  }
}
