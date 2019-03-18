package com.cb.Shuo.service;

import com.cb.Shuo.dao.ArticleDao;
import com.cb.Shuo.model.ArticleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleGetService {

  private static final Logger logger = LoggerFactory.getLogger(ArticleGetService.class);

  @Autowired ArticleDao articleDao;


  public List<ArticleModel> getByDateRange(LocalDateTime start, LocalDateTime end){
    return new ArrayList<>();
  }
}
