package com.cb.Shuo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Data
public class LikeModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "v_key")
  private Long vKey;

  @Column(name = "article_id", columnDefinition = "varchar(40)", nullable = false)
  private String articleId;

  @Column(name = "user_id", columnDefinition = "varchar(255)", nullable = false)
  private String userId;

  @Column(name = "type", columnDefinition = "tinyint", nullable = false)
  private int type;
}
