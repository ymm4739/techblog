package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.LikedArticleBean;

import java.util.List;

public interface LikedArticleService {
    List<LikedArticleBean> findByUserID(int userID, int isLiked);
}
