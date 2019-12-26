package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.LikedArticleBean;
import com.zhumingbei.techblog.mapper.ArticleMapper;
import com.zhumingbei.techblog.mapper.LikedArticleMapper;
import com.zhumingbei.techblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private LikedArticleMapper likedArticleMapper;
    @Override
    public List<ArticleBean> getAll() {
        return articleMapper.selectAll();
    }
    @Override
    public void create(ArticleBean article) {
        articleMapper.insert(article);
    }

    @Override
    public ArticleBean findByID(int articleID) {
        return articleMapper.selectByID(articleID);
    }

    @Override
    public void update(ArticleBean article) {
        articleMapper.update(article);
    }

    @Override
    public List<ArticleBean> getArticlesInOneUser(int userID) {
        return articleMapper.selectAllInOneUser(userID);
    }

    @Override
    public List<ArticleBean> getPublishedArticlesInOneUser(int userID) {
        return articleMapper.selectPublishedInOneUser(userID);
    }

    @Override
    public ArticleBean findPublishedByID(int articleID) {
        return articleMapper.selectPublishedByID( articleID);
    }

    @Override
    public List<ArticleBean> getDraftInOneUser(int userID) {
        return articleMapper.selectDraftInOneUser(userID);
    }

    @Override
    public ArticleBean findByArticleID(int articleID) {
        return articleMapper.selectByID(articleID);
    }

    @Override
    public List<LikedArticleBean> findLikedArticlesByUserID(int readerID) {
        return likedArticleMapper.select(readerID, 1);
    }
}
