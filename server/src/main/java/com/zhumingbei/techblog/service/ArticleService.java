package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.ArticleBean;

import java.util.List;

public interface ArticleService {
    List<ArticleBean> getAll();
    void create(ArticleBean article);
    ArticleBean findByID(int articleID);
    void update(ArticleBean article);
    List<ArticleBean> getListInOneUser(int userID);
}
