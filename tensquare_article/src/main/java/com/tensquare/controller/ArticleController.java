package com.tensquare.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.pojo.Article;
import com.tensquare.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 包名：com.tensquare.controller
 * 作者：ChenKai
 * 日期：2020-02-19  12:39
 */

@RestController
@RequestMapping("/article")
@CrossOrigin // 允许跨域访问
public class ArticleController {

    @Autowired(required = false)
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "添加文章成功！");
    }

    @DeleteMapping("/{articleId}")
    public Result delete(@PathVariable("articleId") String articleId) {
        // 调用业务删除, 此处需要注意：实际工作中执行的是逻辑删除（即update状态为不可用），不删除实际数据
        articleService.delete(articleId);
        return new Result(true, StatusCode.OK, "删除文章成功！");
    }

    @PutMapping("/{articleId}")
    public Result update(@PathVariable("articleId") String articleId,@RequestBody Article article) {
        article.setId(articleId);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改文章成功！");
    }

    @GetMapping("/{articleId}")
    public Result findById(@PathVariable("articleId") String id) {
        Article article=articleService.findById(id);
        return new Result(true, StatusCode.OK, "查询文章成功！",article);
    }

    @GetMapping
    public Result findAll() {
        List<Article> list=articleService.findAll();
        return new Result(true, StatusCode.OK, "查询文章成功！",list);
    }

    @PostMapping("/search/{page}/{size}")
    public Result findPage(@RequestBody Article article, @PathVariable int page, @PathVariable int size){
        // 调用业务分页条件查询, total, rows
        PageResult<Article> pageResult = articleService.findPage(page,size, article);
        // 返回结果
        return new Result(true, StatusCode.OK,"分页查询成功", pageResult);
    }

}
