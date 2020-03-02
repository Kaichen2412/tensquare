package com.tensquare.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.dao.ArticleDao;
import com.tensquare.entity.PageResult;
import com.tensquare.pojo.Article;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 包名：com.tensquare.service
 * 作者：ChenKai
 * 日期：2020-02-19  12:40
 */
@Service
public class ArticleService {
    @Autowired(required = false)
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Article article) {
        //设置参数
        article.setId(idWorker.nextId() + "");
        //设置不公开
        article.setIspublic("0");
        //设置点赞数
        article.setThumbup(0);
        //设置时间
        article.setCreatetime(new Date());
        //设置未审核`
        article.setState("0");
        //不置顶
        article.setIstop("0");
        //评论数
        article.setComment(0);
        //浏览数
        article.setVisits(0);
        articleDao.insert(article);
    }

    public void delete(String articleId) {
        articleDao.deleteById(articleId);
    }

    public void update(Article article) {
        article.setUpdatetime(new Date());
        articleDao.updateById(article);
    }

    public Article findById(String id) {
        return articleDao.selectById(id);
    }

    public List<Article> findAll() {
        return articleDao.selectList(null);
    }

    public PageResult<Article> findPage(int page, int size, Article article) {
        //分页信息构建
        Page<Article> articlePage = new Page<>();

        // 条件构建
        EntityWrapper wrapper = new EntityWrapper();

        //条件判断
        if(!StringUtils.isEmpty(article.getUserid())){
            // 作者id不为空
            // where userid=值
            wrapper.eq("userid", article.getUserid());
        }
        if(!StringUtils.isEmpty(article.getTitle())){
            // 标题不为空，实现模糊查询
            // where tile like %s%
            // SqlLike.DEFAULT 左右两边自动补上%
            wrapper.like("title", article.getTitle());
        }

        List<Article> list = articleDao.selectPage(articlePage, wrapper);

        return new PageResult<>(articlePage.getTotal(),list);
    }
}
