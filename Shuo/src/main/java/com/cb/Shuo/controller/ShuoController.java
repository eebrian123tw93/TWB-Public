package com.cb.Shuo.controller;

import com.cb.Shuo.model.ArticleModel;
import com.cb.Shuo.model.LikeModel;
import com.cb.Shuo.model.UserModel;
import com.cb.Shuo.model.json.ArticleJson;
import com.cb.Shuo.service.ArticleGetService;
import com.cb.Shuo.service.ArticlePostService;
import com.cb.Shuo.service.LikeCommentService;
import com.cb.Shuo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ShuoController {
  private static final Logger logger = LoggerFactory.getLogger(ShuoController.class);

  private final UserService userService;
  private final ArticlePostService articlePostService;
  private final ArticleGetService articleGetService;
  private final LikeCommentService likeCommentService;

  @Autowired
  public ShuoController(
      UserService userService,
      ArticlePostService articlePostService,
      ArticleGetService articleGetService,
      LikeCommentService likeCommentService) {
    this.userService = userService;
    this.articlePostService = articlePostService;
    this.articleGetService = articleGetService;
    this.likeCommentService = likeCommentService;
  }

  @RequestMapping(value = "/public/register", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserModel userModel) {
    logger.info("register " + userModel.getUserId());
    int code = userService.register(userModel);

    if (code == 1) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("userId already exists");
    else if (code == 2)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email already exists");
    else return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/checkUserExist", method = RequestMethod.GET)
  public String checkUserExist() {
    return "yes";
  }

  @RequestMapping(value = "/public/forgotPassword", method = RequestMethod.GET)
  public ResponseEntity forgotPassword(@RequestParam(name = "email") String email) {
    if (userService.forgotPassword(email)) return new ResponseEntity(HttpStatus.NO_CONTENT);
    else return new ResponseEntity(HttpStatus.FORBIDDEN);
  }

  @RequestMapping(value = "/postArticle", method = RequestMethod.POST)
  public ResponseEntity postArticle(@RequestBody ArticleJson articleJson) {
    logger.info("postArticle " + articleJson.getUserId());
    articlePostService.postArticle(articleJson);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/getArticlesFiltered", method = RequestMethod.GET)
  public List<ArticleModel> getArticlesFiltered(
      @RequestParam(name = "startTime", required = false) LocalDateTime start,
      @RequestParam(name = "endTime", required = false) LocalDateTime end,
      @RequestParam(name = "limit", required = false) Integer limit,
      @RequestParam(name = "offset", required = false) Integer offset,
      @RequestParam(name = "orderBy", required = false) String orderBy) {
    return articleGetService.getAll();
  }

  @RequestMapping(value = "/public/getArticles", method = RequestMethod.GET)
  public List<ArticleModel> getArticles() {
    return articleGetService.getAll();
  }

  // todo: add secure api for get article

  //  public List<>

  @RequestMapping(value = "/like", method = RequestMethod.POST)
  public ResponseEntity like(@RequestBody LikeModel likeModel) {
    if (likeCommentService.addLike(likeModel) == 0)
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("article does not exist");
  }

  @RequestMapping(value = "/t", method = RequestMethod.GET)
  public String t() {
    // for testing
    return "hello";
  }

  // todo: search article api
}
