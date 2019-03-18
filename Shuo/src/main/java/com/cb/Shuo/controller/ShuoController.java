package com.cb.Shuo.controller;

import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.model.UserModel;
import com.cb.Shuo.service.ArticlePostService;
import com.cb.Shuo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShuoController {
  private static final Logger logger = LoggerFactory.getLogger(ShuoController.class);

  private final UserService userService;
  private final ArticlePostService articlePostService;

  @Autowired
  public ShuoController(UserService userService, ArticlePostService articlePostService) {
    this.userService = userService;
    this.articlePostService = articlePostService;
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserModel userModel) {
    logger.info("register " + userModel.getUserId());
    if (userService.register(userModel)) return new ResponseEntity(HttpStatus.NO_CONTENT);
    else return new ResponseEntity(HttpStatus.FORBIDDEN);
  }

  @RequestMapping(value = "/checkUserExist", method = RequestMethod.GET)
  public String checkUserExist() {
    return "yes";
  }

  @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
  public ResponseEntity forgotPassword(@RequestParam(name = "email") String email) {
    if (userService.forgotPassoword(email))
      return new ResponseEntity(HttpStatus.OK);
    else return new ResponseEntity(HttpStatus.FORBIDDEN);
  }

  @RequestMapping(value = "/postArticle", method = RequestMethod.POST)
  public ResponseEntity postArticle(@RequestBody ArticleModel articleModel) {
    logger.info("postArticle " + articleModel.getUserId());
    articlePostService.postArticle(articleModel);
    return new ResponseEntity(HttpStatus.OK);
  }

  //  @RequestMapping(value = "/getArticles", method = RequestMethod.GET)
  //  public List<ArticleModel> getArticles(){
  //
  //  }

  @RequestMapping(value = "/t", method = RequestMethod.GET)
  public String t() {
    // for testing
    return "hello";
  }
}
