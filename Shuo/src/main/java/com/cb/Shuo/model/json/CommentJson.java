package com.cb.Shuo.model.json;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentJson {
  private String userId;
  private String articleId;
  private String comment;
  private LocalDateTime commentTime;
}
