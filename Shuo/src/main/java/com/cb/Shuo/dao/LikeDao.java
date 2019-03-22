package com.cb.Shuo.dao;

import com.cb.Shuo.model.LikeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDao extends JpaRepository<LikeModel, Long> {}
