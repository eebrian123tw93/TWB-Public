package com.cb.Shuo.dao;

import com.cb.Shuo.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<CommentModel, Long> {}
