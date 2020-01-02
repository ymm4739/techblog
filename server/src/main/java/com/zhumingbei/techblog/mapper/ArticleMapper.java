package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.ArticleBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    List<ArticleBean> selectAll(int offset, int limit);

    void insert (ArticleBean article);

    ArticleBean selectByIds(int userID, int articleID);
    ArticleBean selectByID(int articleID);
    void update(ArticleBean article);

    List<ArticleBean> selectAllInOneUser(int authorID, int offset, int limit, String sort, String order, String search);
    List<ArticleBean> selectPublishedInOneUser(int userID);

    ArticleBean selectPublishedByID(int articleID);

    List<ArticleBean> selectDraftInOneUser(int userID);

    int count(int authorID, String search);

    List<ArticleBean> selectThumbsArticles(int userID, int offset, int limit, String search);

    int countThumbs(int userID, String search);

    List<ArticleBean> selectCollectedArticles(int userID, int offset, int limit, String search);

    int countCollectedArticles(int userID, String search);

    List<ArticleBean> selectCommentArticles(int userID, int offset, int limit, String search);

    int countCommentArticles(int userID, String search);
}
