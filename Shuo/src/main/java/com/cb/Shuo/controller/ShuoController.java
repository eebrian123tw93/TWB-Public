package com.cb.Shuo.controller;

import com.cb.Shuo.model.UserModel;
import com.cb.Shuo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShuoController {
  private static final Logger logger = LoggerFactory.getLogger(ShuoController.class);

  private final UserService userService;

  @Autowired
  public ShuoController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserModel userModel) {
    logger.info(userModel.getUserId());
    userService.register(userModel);
    return new ResponseEntity(HttpStatus.OK);
  }
}
