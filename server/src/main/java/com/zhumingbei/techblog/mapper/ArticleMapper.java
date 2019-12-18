package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.ArticleBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    List<ArticleBean> selectAll();

    int insert (ArticleBean article);

    ArticleBean selectByID(int articleID);
}
