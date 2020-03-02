package com.tensquare.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.pojo.Article;
import com.tensquare.pojo.Comment;
import com.tensquare.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 包名：com.tensquare.controller
 * 作者：ChenKai
 * 日期：2020-02-19  12:39
 */

@RestController
@RequestMapping("/comment")
@CrossOrigin // 允许跨域访问
public class CommentController {

    @Autowired(required = false)
    private CommentService commentService;

    @PostMapping
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "添加评论成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        commentService.delete(id);
        return new Result(true, StatusCode.OK, "删除评论成功！");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") String id,@RequestBody Comment comment) {
        comment.set_id(id);
        commentService.update(comment);
        return new Result(true, StatusCode.OK, "修改评论成功！");
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") String id) {
        Comment comment=commentService.findById(id);
        return new Result(true, StatusCode.OK, "查询评论成功！",comment);
    }

    @GetMapping
    public Result findAll() {
        List<Comment> list=commentService.findAll();
        return new Result(true, StatusCode.OK, "查询评论成功！",list);
    }

    /**
     * 根据文章id查询评论
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public Result findByArticleId(@PathVariable("articleId") String articleId) {
        List<Comment> list=commentService.findByArticleId(articleId);
        return new Result(true, StatusCode.OK, "查询评论成功！",list);
    }

    /**
     * 评论点赞
     * @param id
     * @return
     */
    @PutMapping("/thumbup/{id}")
    public Result thumbup(@PathVariable("id") String id) {
        commentService.thumbup(id);
        return new Result(true, StatusCode.OK, "点赞成功！");
    }
}
