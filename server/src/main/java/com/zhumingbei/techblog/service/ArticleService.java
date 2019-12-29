package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.LikedArticleBean;

import java.util.List;

public interface ArticleService {
    List<ArticleBean> getAll();
    void create(ArticleBean article);
    ArticleBean findByID(int articleID);
    ArticleBean findPublishedByID(int articleID);
    void update(ArticleBean article);
    List<ArticleBean> getArticlesInOneUser(int authorID, int offset, int limit, String sort, String order, String search);
    List<ArticleBean> getPublishedArticlesInOneUser(int userID);
    List<ArticleBean> getDraftInOneUser(int userID);
    int count(int authorID, String search);
    List<LikedArticleBean> findLikedArticlesByUserID(int readerID);
    List<ArticleBean> getThumbsArticles(int userID, int offset, int limit, String search);
    int countThumbs(int userID, String search);
}
