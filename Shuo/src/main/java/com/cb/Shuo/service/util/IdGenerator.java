package com.cb.Shuo.service.util;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {

  public static void main(String[] args) {
    System.out.println(generateArticleId());
  }

  public static String generateArticleId() {
    return "article-" + UUID.randomUUID().toString().replace("-", "");
  }
}
