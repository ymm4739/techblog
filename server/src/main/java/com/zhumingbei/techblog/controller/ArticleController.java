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

    @PostMapping("/article/create")
    public ApiResponse create(String title, String content, String html) {
        log.debug("html: {}", html);
        int userID = CustomUserPrincipal.getUserID();
        ArticleBean article = new ArticleBean();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorID(userID);
        article.setHtml(html);
        articleService.create(article);
        return ApiResponse.ofSuccess("文章新建成功");
    }
    @PostMapping("/article/edit/{id}")
    public ApiResponse edit(@PathVariable("id") int id, String title, String content, String html) {
        ArticleBean article = articleService.findByID(id);
        if (article == null) {
            return ApiResponse.of(40000, "文章不存在");
        }
        article.setTitle(title);
        article.setContent(content);
        article.setHtml(html);

        articleService.update(article);
        return ApiResponse.ofSuccess("文章编辑成功");
    }
    @GetMapping("/article/show/{articleID}")
    public ArticleBean show(@PathVariable("articleID") int articleID) {
        return articleService.findByID(articleID);
    }
}
