package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.ArticleBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    List<ArticleBean> selectAll();

    void insert (ArticleBean article);

    ArticleBean selectByID(int articleID);

    void update(ArticleBean article);
}
