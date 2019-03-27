package com.cb.Shuo.model.json;

import lombok.Data;

@Data
public class LikeJson {
  private String userId;
  private String articleId;
  private int type;
}
