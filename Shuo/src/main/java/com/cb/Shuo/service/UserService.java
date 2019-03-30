package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.dao.CommentDao;
import com.cb.Shuo.dao.LikeDao;
import com.cb.Shuo.dao.UserDao;
import com.cb.Shuo.model.entity.ArticleModel;
import com.cb.Shuo.model.entity.CommentModel;
import com.cb.Shuo.model.entity.LikeModel;
import com.cb.Shuo.model.entity.UserModel;
import com.cb.Shuo.service.util.SendMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserDao userDao;

  @Autowired private ArticleDao articleDao;
  @Autowired private LikeDao likeDao;
  @Autowired private CommentDao commentDao;

  @Autowired
  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public int register(UserModel userModel) {
    if (userDao.findUserModelByUserId(userModel.getUserId()) != null
        || userModel.getUserId().equals("[deleted]")) {
      logger.info("userId already exists");
      return 1;
    } else if (userModel.getEmail() != null
        && userDao.findUserModelByEmail(userModel.getEmail()) != null) {
      logger.info("email already exists");
      return 2;
    } else {
      userDao.save(userModel);
      logger.info("successfully registered user " + userModel.getUserId());
      return 0;
    }
  }

  public boolean forgotPassword(String email) {
    UserModel user = userDao.findUserModelByEmail(email);
    if (user != null) {
      // send email
      String emailContent = "Your account info:\n" + user.getUserId() + "\n" + user.getPassword();
      SendMail.sendEmail(email, emailContent);
      return true;
    } else return false;
  }

  public void deleteUser(String userId) {
    UserModel userModel = userDao.findUserModelByUserId(userId);
    userDao.delete(userModel);
    List<ArticleModel> articleModelList =
        articleDao.getArticleModelsByUserIdOrderByCreateTimeDesc(userId);
    articleModelList.forEach(articleModel -> articleModel.setUserId("[deleted]"));
    articleDao.saveAll(articleModelList);

    List<LikeModel> likeModelList = likeDao.findAllByUserId(userId);
    likeModelList.forEach(likeModel -> likeModel.setUserId("[deleted]"));
    likeDao.saveAll(likeModelList);

    List<CommentModel> commentModelList = commentDao.findCommentModelsByUserId(userId);
    commentModelList.forEach(commentModel -> commentModel.setUserId("[deleted]"));
    commentDao.saveAll(commentModelList);
  }
}
