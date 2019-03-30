package com.cb.Shuo.model.json;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDataJson {
  private int points;
  private int views;
  private int commentCount;
  private List<CommentJson> comments;
}
