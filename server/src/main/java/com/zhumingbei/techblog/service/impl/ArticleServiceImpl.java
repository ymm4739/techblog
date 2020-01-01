package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.LikedArticleBean;
import com.zhumingbei.techblog.mapper.ArticleMapper;
import com.zhumingbei.techblog.mapper.CollectionMapper;
import com.zhumingbei.techblog.mapper.LikedArticleMapper;
import com.zhumingbei.techblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private LikedArticleMapper likedArticleMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public List<ArticleBean> getAll(int offset, int limit) {
        return articleMapper.selectAll(offset, limit);
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
    public List<ArticleBean> getArticlesInOneUser(int authorID, int offset, int limit, String sort, String order,  String search) {

        return articleMapper.selectAllInOneUser(authorID, offset, limit, sort, order, search);

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
    public int count(int authorID, String search) {
        return articleMapper.count(authorID, search);
    }
    @Override
    public List<LikedArticleBean> findLikedArticlesByUserID(int readerID) {
        return likedArticleMapper.select(readerID, 1);
    }

    @Override
    public List<ArticleBean> getThumbsArticles(int userID, int offset, int limit, String search) {
        return articleMapper.selectThumbsArticles(userID, offset, limit, search);
    }

    @Override
    public int countThumbs(int userID, String search) {
        return articleMapper.countThumbs(userID, search);
    }


    @Override
    public List<ArticleBean> getCollectedArticles(int userID, int offset, int limit, String search) {
        return articleMapper.selectCollectedArticles(userID, offset, limit, search);
    }

    @Override
    public int countCollectedArticles(int userID, String search) {
        return articleMapper.countCollectedArticles(userID, search);
    }

    @Override
    public void collect(int articleID, int userID, int isCollected) {
        int result = collectionMapper.update(articleID, userID, isCollected);
        if (result == 0) {
            collectionMapper.insert(articleID, userID, isCollected);
        }
    }

    @Override
    public List<Integer> findCollectedArticleIDs(int userID) {
        return collectionMapper.selectByUserID(userID);
    }
}
