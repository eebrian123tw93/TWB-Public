package com.cb.Shuo.controller;

import com.cb.Shuo.model.entity.UserModel;
import com.cb.Shuo.model.json.ArticleDataJson;
import com.cb.Shuo.model.json.ArticleJson;
import com.cb.Shuo.model.json.CommentJson;
import com.cb.Shuo.model.json.LikeJson;
import com.cb.Shuo.service.ArticleGetService;
import com.cb.Shuo.service.ArticlePostService;
import com.cb.Shuo.service.LikeCommentService;
import com.cb.Shuo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class ShuoController {
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

  // <editor-fold defaultstate="collapsed" desc="user">
  @RequestMapping(value = "/public/register", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserModel userModel) {
    log.info("register " + userModel.getUserId());
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

  @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
  public ResponseEntity deleteUser(Principal principal) {
    log.info("de-register " + principal.getName());
    userService.deleteUser(principal.getName());
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="article">
  @RequestMapping(value = "/postArticle", method = RequestMethod.POST)
  public ResponseEntity postArticle(@RequestBody ArticleJson articleJson, Principal principal) {
    log.info("postArticle " + principal.getName());
    articlePostService.postArticle(articleJson, principal.getName());
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/getArticles", method = RequestMethod.GET)
  public List<ArticleJson> getArticles(
      @RequestParam(name = "startTime", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime startTime,
      @RequestParam(name = "endTime", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime endTime,
      @RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit,
      @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
      @RequestParam(name = "orderBy", required = false, defaultValue = "likes") String orderBy,
      Principal principal) {

    if (startTime == null) {
      startTime = LocalDateTime.now().minusHours(12);
      endTime = startTime.plusHours(12);
    }

    String userId = principal.getName();

    log.info(
        "getArticlesFiltered "
            + startTime
            + " "
            + endTime
            + " "
            + limit
            + " "
            + offset
            + " "
            + userId);

    return articleGetService.getArticles(startTime, endTime, limit, offset, userId, orderBy);
  }

  @RequestMapping(value = "/public/getArticles", method = RequestMethod.GET)
  public List<ArticleJson> getArticlesPublic(
      @RequestParam(name = "startTime", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime startTime,
      @RequestParam(name = "endTime", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime endTime,
      @RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit,
      @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
      @RequestParam(name = "orderBy", required = false, defaultValue = "likes") String orderBy) {
    if (startTime == null) {
      startTime = LocalDateTime.now().minusHours(12);
      endTime = startTime.plusHours(12);
    }

    log.info("getArticles " + startTime + " " + endTime + " " + limit + " " + offset);
    return articleGetService.getArticles(startTime, endTime, limit, offset, null, orderBy);
  }

  @RequestMapping(value = "/public/getUserPostHistory", method = RequestMethod.GET)
  public List<ArticleJson> getUserPostHistory(@RequestParam(value = "userId") String userId) {
    return articleGetService.getArticlesByAuthor(userId);
  }

  @RequestMapping(value = "/public/getArticleData", method = RequestMethod.GET)
  public ArticleDataJson getArticleData(@RequestParam(value = "articleId") String articleId) {
    return articleGetService.getArticleData(articleId);
  }

  @RequestMapping(value = "/public/viewed", method = RequestMethod.POST)
  public ResponseEntity viewed(@RequestBody String articleId) {
    log.info("viewed " + articleId);
    articlePostService.viewArticle(articleId);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/public/getComments", method = RequestMethod.GET)
  @Deprecated
  public List<CommentJson> getComments(@RequestParam(value = "articleId") String articleId) {
    return likeCommentService.getComments(articleId);
  }

  @RequestMapping(value = "/like", method = RequestMethod.POST)
  public ResponseEntity like(@RequestBody LikeJson likeJson, Principal principal) {
    if (likeCommentService.like(likeJson, principal.getName()) == 0)
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("article does not exist");
  }

  @RequestMapping(value = "/comment", method = RequestMethod.POST)
  public ResponseEntity comment(@RequestBody CommentJson commentJson, Principal principal) {
    log.info("comment");
    if (likeCommentService.comment(commentJson, principal.getName()) == 0)
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("article does not exist");
  }
  // </editor-fold>

  @RequestMapping(value = "/t", method = RequestMethod.GET)
  public String t() {
    // for testing
    return "hello";
  }
}
