package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.LikedArticleBean;
import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.service.ArticleService;
import com.zhumingbei.techblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Slf4j
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;



    @GetMapping("/article")
    public List<ArticleBean> getList() {
        return articleService.getAll();
    }

    @GetMapping("/article/list")
    public List<ArticleBean> getAllArticlesByAuthor(int authorID) {
        return articleService.getArticlesInOneUser(authorID);
    }

    @GetMapping("/article/index/{authorID}")
    public ApiResponse getPublishedArticlesByAuthor(@PathVariable("authorID") int authorID, @RequestParam(required = false) Integer readerID) {
        List<ArticleBean> articleBeans = articleService.getPublishedArticlesInOneUser(authorID);
        List<Integer> likedArticleID = getLikedArticlesID(readerID);
        HashMap result = new HashMap();
        result.put("articles", articleBeans);
        result.put("likes", likedArticleID);
        return ApiResponse.ofSuccess(result);
    }

    @PostMapping("/article/create")
    public ApiResponse create(int authorID, String title, String content, String html, String summary, String summaryImage, boolean isPublished) {
        ArticleBean article = new ArticleBean();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorID(authorID);
        article.setHtml(html);
        article.setIsPublished(isPublished ? 1 : 0);
        if (summary.isEmpty()) {
            summary = generateSummary(html);
        }
        article.setSummary(summary);
        article.setSummaryImage(summaryImage);
        articleService.create(article);
        return isPublished ? ApiResponse.ofSuccess("文章已发布") : ApiResponse.ofSuccess("文章已保存为草稿");
    }
    @PostMapping("/article/save/{articleID}")
    public ApiResponse save( @PathVariable("articleID") int articleID, String title, String content, String html, String summary, String summaryImage, boolean isPublished) {
        ArticleBean article = articleService.findByID(articleID);
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
        article.setSummaryImage(summaryImage);
        articleService.update(article);
        return ApiResponse.ofSuccess("文章编辑成功");
    }

    @GetMapping("/article/{articleID}")
    public ArticleBean getContent(@PathVariable("articleID") int articleID) {
        return articleService.findByID(articleID);
    }
    @GetMapping("/article/show/{articleID}")
    public ApiResponse show(@PathVariable("articleID") int articleID, @RequestParam(required = false) Integer readerID) {
        ArticleBean articleBean = articleService.findPublishedByID(articleID);
        List<Integer> likedArticlesID = getLikedArticlesID(readerID);

        HashMap result = new HashMap();
        result.put("article", articleBean);
        result.put("likes", likedArticlesID);
        return ApiResponse.ofSuccess(result);
    }



    @PostMapping("/article/delete/{articleID}")
    public ApiResponse delete( @PathVariable("articleID") int articleID) {
        ArticleBean articleBean = articleService.findByID(articleID);
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

    private List<Integer> getLikedArticlesID(Integer readerID) {
        if (readerID == null) {
            return new ArrayList<>();
        }
        List<LikedArticleBean> likedArticleBeans = articleService.findLikedArticlesByUserID(readerID);
        List<Integer> likedArticleID = new ArrayList<>();
        for (LikedArticleBean article : likedArticleBeans) {
            likedArticleID.add(article.getArticleID());
        }
        return likedArticleID;
    }
}
