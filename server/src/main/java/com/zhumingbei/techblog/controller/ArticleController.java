package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.service.ArticleService;
import com.zhumingbei.techblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("article/edit")
    public ApiResponse edit(String title, String content) {
        int userID = CustomUserPrincipal.getUserID();
        ArticleBean article = new ArticleBean();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorID(userID);

        articleService.create(article);
        return ApiResponse.ofSuccess("文章编辑成功");
    }
    @GetMapping("/article/show/{articleID}")
    public ArticleBean show(@PathVariable("articleID") int articleID) {
        int ID = Integer.valueOf(articleID);
        return articleService.findByID(articleID);
    }
}
