package com.cb.Shuo.controller;

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
    log.debug("register " + userJson.getUserId());
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
    log.debug("de-register " + principal.getName());
    userService.deleteUser(principal.getName());
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="article">
  @RequestMapping(value = "/postArticle", method = RequestMethod.POST)
  public ResponseEntity postArticle(@RequestBody ArticleJson articleJson, Principal principal) {
    log.debug("postArticle " + principal.getName());
    String articleId = articlePostService.postArticle(articleJson, principal.getName());
    return ResponseEntity.status(202).body(articleId);
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
  @Deprecated
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
    log.debug(
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

  @ApiOperation("Retrieve json array of articles. No auth or Basic auth are accepted.")
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
      @RequestParam(name = "orderBy", required = false, defaultValue = "points") String orderBy,
      Principal principal) {

    String userId = null;

    if (principal != null) {
      userId = principal.getName();
      log.debug("getArticles user " + userId);
    }

    if (startTime == null) {
      startTime = LocalDateTime.now().minusHours(12);
      endTime = startTime.plusHours(12);
    }

    log.debug(
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

    return articleGetService.getArticles(startTime, endTime, limit, offset, userId, orderBy);
  }

  @RequestMapping(value = "/public/searchArticle", method = RequestMethod.GET)
  public List<ArticleJson> searchArticle(
      @RequestParam(name = "keyWord") String keyWord,
      @RequestParam(name = "limit", required = false, defaultValue = "1000") Integer limitNum,
      @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
    log.debug("search keyword " + keyWord);
    return articleGetService.searchArticles(keyWord, limitNum, offset);
  }

  @RequestMapping(value = "/public/getUserPostHistory", method = RequestMethod.GET)
  public List<ArticleJson> getUserPostHistory(
      @RequestParam(name = "authorId") String authorId, Principal principal) {
    String userId = null;
    if (principal != null) {
      userId = principal.getName();
      log.debug("auth user " + userId);
    }

    log.debug("getUserPostHistory for user " + authorId);
    return articleGetService.getArticlesByAuthor(authorId, userId);
  }

  @RequestMapping(value = "/public/getArticleData", method = RequestMethod.GET)
  public ArticleDataJson getArticleDataPublic(@RequestParam(name = "articleId") String articleId) {
    log.debug("getArticleDataPublic " + articleId);
    return articleGetService.getArticleData(articleId, null);
  }

  @RequestMapping(value = "/getArticleData", method = RequestMethod.GET)
  public ArticleDataJson getArticleData(
      @RequestParam(name = "articleId") String articleId, Principal principal) {
    log.debug("getArticleData " + articleId + " " + principal.getName());
    return articleGetService.getArticleData(articleId, principal.getName());
  }

  @RequestMapping(value = "/public/viewed", method = RequestMethod.POST)
  @ApiImplicitParam(name = "articleId", value = "raw string value of articleId (do not use \" \")")
  @ApiResponse(code = 204, message = "success")
  public ResponseEntity viewed(@RequestBody String articleId) {
    log.debug(articleId + " views +1");
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
    log.debug(likeJson.getArticleId() + " points " + likeJson.getType());
    if (likeCommentService.like(likeJson, principal.getName()) == 0)
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("article does not exist");
  }

  @RequestMapping(value = "/comment", method = RequestMethod.POST)
  public ResponseEntity comment(@RequestBody CommentJson commentJson, Principal principal) {
    log.debug("comment by " + principal.getName() + " on article " + commentJson.getArticleId());
    if (likeCommentService.comment(commentJson, principal.getName()) == 0)
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("article does not exist");
  }
  // </editor-fold>

  @ApiOperation("test if server is responding")
  @RequestMapping(value = "/t", method = RequestMethod.GET)
  public String t() {
    // for testing
    return "hello";
  }
}
