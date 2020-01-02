package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
@RestController
public class CollectionController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/collection/list")
    public HashMap<String, Object> list (int userID, int offset, int limit, String search) {
        if (search.isEmpty()) {
            search = null;
        }
        List<ArticleBean> articleBeans = articleService.getCollectedArticles(userID, offset, limit, search);
        int total = articleService.countCollectedArticles(userID, search);
        HashMap<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("articles", articleBeans);
        return result;
    }

    @PostMapping("/collection")
    public ApiResponse collect(int articleID, int userID, boolean addOne) {
        ArticleBean articleBean = articleService.findByID(articleID);
        if (articleBean == null) {
            return ApiResponse.of(40000, "该文章不存在");
        }
        int isCollected = 0;
        if (addOne) {
            isCollected = 1;
        }
        int num = addOne ? 1 : -1;
        articleBean.setCollectedNums(articleBean.getCollectedNums() + num);
        articleService.update(articleBean);
        articleService.collect(articleID, userID, isCollected);
        String message = addOne ? "收藏成功" : "取消收藏";
        return ApiResponse.ofSuccess(message);
    }
}
