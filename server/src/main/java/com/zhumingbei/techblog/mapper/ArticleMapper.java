package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.ArticleBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    List<ArticleBean> selectAll();

    void insert (ArticleBean article);

    ArticleBean selectByIds(int userID, int articleID);
    ArticleBean selectByID(int articleID);
    void update(ArticleBean article);

    List<ArticleBean> selectAllInOneUser(int userID);

    List<ArticleBean> selectPublishedInOneUser(int userID);

    ArticleBean selectPublishedByID(int userID, int articleID);

    List<ArticleBean> selectDraftInOneUser(int userID);
}
