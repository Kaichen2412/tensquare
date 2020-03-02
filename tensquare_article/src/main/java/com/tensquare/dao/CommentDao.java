package com.tensquare.dao;

import com.tensquare.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 包名：com.tensquare.dao
 * 作者：ChenKai
 * 日期：2020-02-19  12:39
 */
public interface CommentDao extends MongoRepository<Comment,String> {
    List<Comment> findByArticleid(String articleId);
}
