package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.ArticleBean;

import java.util.List;

public interface ArticleService {
    List<ArticleBean> getAll();
    void create(ArticleBean article);
    ArticleBean findByID(int userID, int articleID);
    ArticleBean findPublishedByID(int userID, int articleID);
    void update(ArticleBean article);
    List<ArticleBean> getArticlesInOneUser(int userID);
    List<ArticleBean> getPublishedArticlesInOneUser(int userID);
    List<ArticleBean> getDraftInOneUser(int userID);
}
