package com.cb.Shuo.model.json;

import lombok.Data;

@Data
public class CommentJson {
  private String userId;
  private String articleId;
  private String comment;
}
