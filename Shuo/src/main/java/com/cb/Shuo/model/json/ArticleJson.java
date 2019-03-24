package com.cb.Shuo.model.json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleJson {
  private String articleId;
  private String content;
  private List<String> images;
  private String userId;
  private String title;
  private int views;
  private int points;
  private int commentCount;
  private LocalDateTime createTime;
}
