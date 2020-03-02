package com.tensquare.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.dao.ArticleDao;
import com.tensquare.dao.CommentDao;
import com.tensquare.entity.PageResult;
import com.tensquare.pojo.Article;
import com.tensquare.pojo.Comment;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 包名：com.tensquare.service
 * 作者：ChenKai
 * 日期：2020-02-19  12:40
 */
@Service
public class CommentService {
    @Autowired(required = false)
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(Comment comment) {
        comment.set_id(idWorker.nextId()+"");
        comment.setThumbup(0);
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }

    public void delete(String id) {
        commentDao.deleteById(id);
    }

    public void update(Comment comment) {
        // 如果前端传递过来的数据不完整，将会导致更新后的这个文档丢失缺失的属性
        // mongodb里的update(条件，更新后的值)，这种是覆盖式更新
        // 1. 使用updater更新器$set, mongoTemplate 使用的是mongo命令
        // Query query, 条件
        Query query = new Query();
        // 构建BasicDbObject
        query.addCriteria(Criteria.where("_id").is(comment.get_id()));
        // Update update 更新后的值， 使用更新器4set
        Update update = Update.update("content", comment.getContent());
        // String collectionName 集合的名称
        mongoTemplate.updateFirst(query,update,"comment");

    }

    public Comment findById(String id) {
        Optional<Comment> optional = commentDao.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    public List<Comment> findByArticleId(String articleId) {
        List<Comment> comments = commentDao.findByArticleid(articleId);
        return comments;
    }

    public void thumbup(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"comment");
    }
}
