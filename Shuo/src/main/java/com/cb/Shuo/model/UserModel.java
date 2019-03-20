package com.cb.Shuo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "v_key")
  private Long vKey;

  @Column(name = "user_id", unique = true, columnDefinition = "varchar(255)")
  @NotNull
  private String userId;

  @Column(name = "password", columnDefinition = "varchar(255)")
  @NotNull
  private String password;

  @Column(name = "email", columnDefinition = "varchar(255)")
  private String email;
}
