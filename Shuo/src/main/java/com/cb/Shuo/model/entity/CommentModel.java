package com.cb.Shuo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class CommentModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "v_key")
  private Long vKey;

  @Column(name = "article_id", columnDefinition = "varchar(40)", nullable = false)
  private String articleId;

  @Column(name = "user_id", columnDefinition = "varchar(255)", nullable = false)
  private String userId;

  @Column(name = "comment", columnDefinition = "varchar(500)", nullable = false)
  private String comment;

  @Column(name = "create_time", columnDefinition = "datetime", nullable = false)
  private LocalDateTime createTime;
}
