package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.service.ArticleService;
import com.zhumingbei.techblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/article/list")
    public List<ArticleBean> getList() {
        return articleService.getAll();
    }

    @GetMapping("/user/{userID}/article/list")
    public List<ArticleBean> getArticlesByUserID(@PathVariable("userID") int userID) {
        return articleService.getListInOneUser(userID);
    }

    @GetMapping("/user/{userID}/article/index")
    public List<ArticleBean> articleListOfUser(@PathVariable("userID") int userID) {
        return getArticlesByUserID(userID);
    }

    @PostMapping("/user/{userID}/article/create")
    public ApiResponse create(@PathVariable("userID") int userID, String title, String content, String html, String summary) {
        log.debug("html: {}", html);
        ArticleBean article = new ArticleBean();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorID(userID);
        article.setHtml(html);
        if (summary.isEmpty()) {
            summary = generateSummary(html);
        }
        article.setSummary(summary);
        articleService.create(article);
        return ApiResponse.ofSuccess("文章新建成功");
    }
    @PostMapping("/user/{userID}/article/edit/{id}")
    public ApiResponse edit(@PathVariable("userID") int userID, @PathVariable("id") int id, String title, String content, String html, String summary) {
        ArticleBean article = articleService.findByID(id);
        if (article == null) {
            return ApiResponse.of(40000, "文章不存在");
        }
        article.setTitle(title);
        article.setContent(content);
        article.setHtml(html);
        if (summary.isEmpty()) {
            summary = generateSummary(html);
        }
        article.setSummary(summary);
        articleService.update(article);
        return ApiResponse.ofSuccess("文章编辑成功");
    }
    @GetMapping("/user/{userID}/article/show/{articleID}")
    public ArticleBean show(@PathVariable("userID") int userID, @PathVariable("articleID") int articleID) {
        return articleService.findByID(articleID);
    }


    private String generateSummary(String html) {
        String summary = "";
        int max = Math.min(200, html.length());
        String front = html.substring(0, max);
        if (front.matches("<img.*?src=\".*\".*>")) {
            summary = front.replaceAll("<img.*?src=\".*\".*>", "");
        } else {
            summary = front;
        }
        return summary;
    }
}
