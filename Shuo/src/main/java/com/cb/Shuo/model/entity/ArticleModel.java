package com.cb.Shuo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Data
public class ArticleModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "v_key")
  private Long vKey;

  @Column(name = "article_id", columnDefinition = "varchar(40)")
  private String articleId;

  @Column(name = "content", columnDefinition = "varchar(5000)")
  private String content;

  @Column(name = "images", columnDefinition = "varchar(1000)")
  private String images;

  @Column(name = "user_id", columnDefinition = "varchar(255)")
  private String userId;

  @Column(name = "title", columnDefinition = "varchar(50)")
  private String title;

  @Column(name = "points", columnDefinition = "int(11)")
  private int points = 0;

  @Column(name = "views", columnDefinition = "bigint(20)")
  private int views = 0;

  @Column(name = "comment_count", columnDefinition = "int(11)")
  private int commentCount = 0;

  @Column(name = "create_time", columnDefinition = "datetime")
  private LocalDateTime createTime;
}