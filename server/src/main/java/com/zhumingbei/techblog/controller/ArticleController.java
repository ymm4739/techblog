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
        return articleService.getArticlesInOneUser(userID);
    }

    @GetMapping("/user/{userID}/article/index")
    public List<ArticleBean> articleListOfUser(@PathVariable("userID") int userID) {
        return articleService.getPublishedArticlesInOneUser(userID);
    }

    @PostMapping("/user/{userID}/article/create")
    public ApiResponse create(@PathVariable("userID") int userID, String title, String content, String html, String summary, boolean isPublished) {
        ArticleBean article = new ArticleBean();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorID(userID);
        article.setHtml(html);
        article.setIsPublished(isPublished ? 1 : 0);
        if (summary.isEmpty()) {
            summary = generateSummary(html);
        }
        article.setSummary(summary);
        articleService.create(article);
        return isPublished ? ApiResponse.ofSuccess("文章已发布") : ApiResponse.ofSuccess("文章已保存为草稿");
    }
    @PostMapping("/user/{userID}/article/save/{articleID}")
    public ApiResponse save(@PathVariable("userID") int userID, @PathVariable("articleID") int articleID, String title, String content, String html, String summary, boolean isPublished) {
        ArticleBean article = articleService.findByID(userID, articleID);
        if (article == null) {
            return ApiResponse.of(40000, "文章不存在");
        }
        article.setTitle(title);
        article.setContent(content);
        article.setHtml(html);
        article.setIsPublished(isPublished ? 1 : 0);
        if (summary.isEmpty()) {
            summary = generateSummary(html);
        }
        article.setSummary(summary);
        articleService.update(article);
        return ApiResponse.ofSuccess("文章编辑成功");
    }

    @GetMapping("/user/{userID}/article/edit/{articleID}")
    public ArticleBean edit(@PathVariable("userID") int userID, @PathVariable("articleID") int articleID) {
        return articleService.findByID(userID, articleID);
    }
    @GetMapping("/user/{userID}/article/show/{articleID}")
    public ArticleBean show(@PathVariable("userID") int userID, @PathVariable("articleID") int articleID) {
        return articleService.findPublishedByID(userID, articleID);
    }

    @PostMapping("/user/{userID}/article/like/{articleID}")
    public ApiResponse like(@PathVariable("userID") int userID, @PathVariable("articleID") int articleID) {
        ArticleBean articleBean = articleService.findPublishedByID(userID, articleID);
        if (articleBean == null) {
            return ApiResponse.of(40400, "文章不存在");
        }
        articleBean.setLikedNums(articleBean.getLikedNums() + 1);
        articleService.update(articleBean);
        return ApiResponse.ofSuccess("点赞文章操作成功");
    }

    @PostMapping("/user/{userID}/article/delete/{articleID}")
    public ApiResponse delete(@PathVariable("userID") int userID, @PathVariable("articleID") int articleID) {
        ArticleBean articleBean = articleService.findByID(userID, articleID);
        if (articleBean == null) {
            return ApiResponse.of(40400, "文章不存在");
        }
        articleBean.setIsDeleted(1);
        articleService.update(articleBean);
        return ApiResponse.ofSuccess("删除文章成功");
    }

    //也可以通过list筛选获取，暂时不被调用，调用之前需要增加权限
    @PostMapping("/user/{userID}/article/draft")
    public List<ArticleBean> draftBox(@PathVariable("userID") int userID) {
        return articleService.getDraftInOneUser(userID);
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
