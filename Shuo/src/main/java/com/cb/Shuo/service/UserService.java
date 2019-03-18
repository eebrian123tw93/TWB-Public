package com.cb.Shuo.service;

import com.cb.Shuo.dao.UserDao;
import com.cb.Shuo.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserDao userDao;

  @Autowired
  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public boolean register(UserModel userModel) {
    if (userDao.findUserModelByUserId(userModel.getUserId()) != null) {
      logger.info("user " + userModel.getUserId() + " already exists");
      return false;
    } else {
      userDao.save(userModel);
      logger.info("successfully registered user " + userModel.getUserId());
      return true;
    }
  }

  public boolean forgotPassoword(String email) {
    UserModel user = userDao.findUserModelByEmail(email);
    if (user != null) {
      // send email
      return true;
    } else return false;
  }
}
