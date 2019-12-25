package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.LikedArticleBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedArticleMapper {
    void insert(int userID, int articleID, int isLiked);
    int update(int userID, int articleID, int isLiked);
    List<LikedArticleBean> select(int userID, int isLiked);
}
