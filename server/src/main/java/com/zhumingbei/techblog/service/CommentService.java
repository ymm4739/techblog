package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.CommentBean;

import java.util.List;

public interface CommentService {
    void comment(int userID, int articleID, int responseID, String content);

    CommentBean findByID(int commentID);

    void update (CommentBean commentBean);

    List<CommentBean> getCommentsOfArticle(int articleID);
}
