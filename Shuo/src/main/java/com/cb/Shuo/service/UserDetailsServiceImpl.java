package com.cb.Shuo.service;

import com.cb.Shuo.dao.UserDao;
import com.cb.Shuo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDao userDao;

  @Autowired
  public UserDetailsServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

    UserModel user = userDao.findUserModelByUserId(userId);
    User.UserBuilder builder;
    if (user != null) {
      builder = User.withUsername(userId);
      builder.disabled(false);
      builder.password(user.getPassword());
      builder.authorities("USER");
    } else {
      throw new UsernameNotFoundException("User not found.");
    }
    return builder.build();
  }
}
