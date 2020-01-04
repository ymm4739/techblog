package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.CommentBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.service.ArticleService;
import com.zhumingbei.techblog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
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
    public ApiResponse comment(int userID, int articleID,  int commentIndex, int answerID, String content) {
        ArticleBean articleBean = articleService.findByID(articleID);
        if (articleBean == null) {
            return ApiResponse.of(40000, "文章不存在");
        }
        int commentID = commentService.comment(userID, articleID, commentIndex, answerID, content);
        if (answerID > 0) {
            CommentBean parentComment = commentService.findParentComment(commentID);
            if (parentComment == null) {
                return ApiResponse.of(40000, "回复的评论不存在");
            }
            parentComment.setReplyNums(parentComment.getReplyNums() + 1);
            commentService.update(parentComment);
        }
        articleBean.setCommentNums(articleBean.getCommentNums() + 1);
        articleService.update(articleBean);
        String message = answerID > 0 ? "回复成功" : "评论成功";
        return ApiResponse.ofSuccess(message);
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
        int deletedNums = 1;
        if (commentBean.getAnswerID() == 0) {
            deletedNums += commentService.countRepliesOfComment(commentBean.getArticleID(), commentBean.getCommentIndex());
        }
        ArticleBean articleBean = articleService.findByID(commentBean.getArticleID());
        articleBean.setCommentNums(articleBean.getCommentNums() - deletedNums);
        articleService.update(articleBean);
        return ApiResponse.ofSuccess("评论删除成功");
    }

    @GetMapping("/comment/index")
    public List<HashMap<String, Object>> getCommentsOfArticle(int articleID, int offset, int limit) {
        return commentService.getCommentsAndReplies(articleID, offset, limit);
    }

    @GetMapping("/comment/reply")
    public List<CommentBean> getRepliesOfComment(int articleID, int commentIndex, int offset, int limit){
        List<CommentBean> replies = commentService.getRepliesOfComment(articleID, commentIndex, offset, limit);
        return replies;
    }

    @PostMapping("/comment/thumbs")
    public ApiResponse thumbs(int commentID, boolean isLiked) {
        CommentBean commentBean = commentService.findByID(commentID);
        if (commentBean == null) {
            return ApiResponse.of(40000, "评论不存在");
        }
        int num = isLiked ? 1 : -1;
        commentBean.setLikedNums(commentBean.getLikedNums() + num);
        commentService.update(commentBean);
        return ApiResponse.ofSuccess("点赞成功");
    }

}
