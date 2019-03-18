package com.cb.Shuo.dao;

import com.cb.Shuo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserModel, Long> {
  UserModel findUserModelByUserId(String userId);

  UserModel findUserModelByEmail(String email);
}
