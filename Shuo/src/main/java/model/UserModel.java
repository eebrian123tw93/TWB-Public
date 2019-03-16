package model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
public class UserModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long key;

  private String userId;
  private String password;
  private String email;
}
