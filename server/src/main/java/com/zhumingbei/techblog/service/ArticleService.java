package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.ArticleBean;

import java.util.List;

public interface ArticleService {
    List<ArticleBean> getAll();
    int create(ArticleBean article);
    ArticleBean findByID(int articleID);
}
