package com.cb.Shuo.controller;

import com.cb.Shuo.model.entity.UserModel;
import com.cb.Shuo.model.json.*;
import com.cb.Shuo.service.ArticleGetService;
import com.cb.Shuo.service.ArticlePostService;
import com.cb.Shuo.service.LikeCommentService;
import com.cb.Shuo.service.UserService;
import io.swagger.annotations.*;
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
@Api(value = "controller for the entire TWB backend")
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
  @ApiOperation(value = "register a new user")
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "successful register"),
        @ApiResponse(code = 403, message = "register fail, id or email already exists")
      })
  @RequestMapping(value = "/public/register", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserJson userJson) {
    log.info("register " + userJson.getUserId());
    int code = userService.register(userJson);

    if (code == 1) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("userId already exists");
    else if (code == 2)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email already exists");
    else return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "check if users exists in database")
  @RequestMapping(value = "/checkUserExist", method = RequestMethod.GET)
  public String checkUserExist() {
    return "yes";
  }

  @ApiOperation(value = "request server to send email containing account details")
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

  @ApiOperation("Retrieve json array of articles. Basic auth required.")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "startTime",
        value = "start time of articles",
        defaultValue = "[current time minus 12 hours]"),
    @ApiImplicitParam(
        name = "endTime",
        value = "end time of articles",
        defaultValue = "[current time]"),
    @ApiImplicitParam(
        name = "orderBy",
        value = "order by \"points\" or \"create_time\"",
        defaultValue = "points")
  })
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
      @RequestParam(name = "orderBy", required = false, defaultValue = "points") String orderBy,
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

  @ApiOperation("Retrieve json array of articles. No auth required.")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "startTime",
        value = "start time of articles",
        defaultValue = "[current time minus 12 hours]"),
    @ApiImplicitParam(
        name = "endTime",
        value = "end time of articles",
        defaultValue = "[current time]"),
    @ApiImplicitParam(
        name = "orderBy",
        value = "order by \"points\" or \"create_time\"",
        defaultValue = "points")
  })
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
      @RequestParam(name = "orderBy", required = false, defaultValue = "points") String orderBy) {
    if (startTime == null) {
      startTime = LocalDateTime.now().minusHours(12);
      endTime = startTime.plusHours(12);
    }

    log.info(
        "getArticleModelsOrderByPoints "
            + startTime
            + " "
            + endTime
            + " "
            + limit
            + " "
            + offset
            + " "
            + orderBy);
    return articleGetService.getArticles(startTime, endTime, limit, offset, null, orderBy);
  }

  @RequestMapping(value = "/public/getUserPostHistory", method = RequestMethod.GET)
  public List<ArticleJson> getUserPostHistory(@RequestParam(name = "userId") String userId) {
    return articleGetService.getArticlesByAuthor(userId);
  }

  @RequestMapping(value = "/public/getArticleData", method = RequestMethod.GET)
  public ArticleDataJson getArticleDataPublic(@RequestParam(name = "articleId") String articleId) {
    log.info("getArticleDataPublic " + articleId);
    return articleGetService.getArticleData(articleId, null);
  }

  // todo: private getArticleData contain like status
  @RequestMapping(value = "/getArticleData", method = RequestMethod.GET)
  public ArticleDataJson getArticleData(
      @RequestParam(name = "articleId") String articleId, Principal principal) {
    log.info("getArticleData " + articleId + " " + principal.getName());
    return articleGetService.getArticleData(articleId, principal.getName());
  }

  @RequestMapping(value = "/public/viewed", method = RequestMethod.POST)
  @ApiImplicitParam(name = "articleId", value = "raw string value of articleId (do not use \" \")")
  @ApiResponse(code = 204, message = "success")
  public ResponseEntity viewed(@RequestBody String articleId) {
    log.info("viewed " + articleId);
    articlePostService.viewArticle(articleId);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/public/getComments", method = RequestMethod.GET)
  @Deprecated
  public List<CommentJson> getComments(@RequestParam(name = "articleId") String articleId) {
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
    log.info("comment by " + principal.getName());
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
