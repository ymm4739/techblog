package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.CommentBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.service.ArticleService;
import com.zhumingbei.techblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/comment/list")
    public HashMap<String, Object> list(int userID, int offset, int limit, String search) {
        if (search.isEmpty()) {
            search = null;
        }
        List<ArticleBean> articleBeans = articleService.getCommentArticles(userID, offset, limit, search);
        int total = articleService.countCommentArticles(userID, search);
        HashMap<String, Object> result = new HashMap<>();
        result.put("articles", articleBeans);
        result.put("total", total);
        return  result;
    }

    @PostMapping("/comment")
    public ApiResponse comment(int userID, int articleID,  int responseID, String content) {
        ArticleBean articleBean = articleService.findByID(articleID);
        if (articleBean == null) {
            return ApiResponse.of(40000, "文章不存在");
        }
        commentService.comment(userID, articleID, responseID, content);
        articleBean.setCommentNums(articleBean.getCommentNums() + 1);
        articleService.update(articleBean);
        return ApiResponse.ofSuccess("评论成功");
    }

    @PostMapping("/comment/delete")
    public ApiResponse delete(int commentID) {
        CommentBean commentBean = commentService.findByID(commentID);
        if (commentBean == null) {
            return ApiResponse.of(40000, "评论不存在");
        }
        if (commentBean.getIsDeleted() == 1) {
            return ApiResponse.of(40000, "评论已被删除，不能重复删除");
        }
        commentBean.setIsDeleted(1);
        commentService.update(commentBean);
        ArticleBean articleBean = articleService.findByID(commentBean.getArticleID());
        articleBean.setCommentNums(articleBean.getCommentNums() - 1);
        articleService.update(articleBean);
        return ApiResponse.ofSuccess("评论删除成功");
    }

}
