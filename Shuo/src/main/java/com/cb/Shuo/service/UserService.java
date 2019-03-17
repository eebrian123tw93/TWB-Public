package com.cb.Shuo.service;

import com.cb.Shuo.dao.UserDao;
import com.cb.Shuo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;

  @Autowired
  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public boolean register(UserModel userModel) {
    userDao.save(userModel);
    return true;
  }
}
