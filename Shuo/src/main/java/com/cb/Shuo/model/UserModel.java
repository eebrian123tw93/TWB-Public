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

  @Column(name = "user_id", unique = true)
  @NotNull
  private String userId;

  @Column(name = "password")
  @NotNull
  private String password;

  @Column(name = "email")
  @NotNull
  private String email;
}
