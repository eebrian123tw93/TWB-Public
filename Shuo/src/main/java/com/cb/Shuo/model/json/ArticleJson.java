package com.cb.Shuo.model.json;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleJson {
  private String articleId;
  private String content;
  private List<String> images;
  private String userId;
  private String title;
  private int points;
  private int views;
  private LocalDateTime createTime;
}
