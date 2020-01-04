package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.CommentBean;

import java.util.HashMap;
import java.util.List;

public interface CommentService {
    int comment(int userID, int articleID, int commentIndex, int answerID, String content);

    void comment(CommentBean commentBean);

    CommentBean findByID(int commentID);

    void update (CommentBean commentBean);

    List<CommentBean> getCommentsOfArticle(int articleID, int offset, int limit);

    int deleteByParent(int articleID, int commentIndex);

    CommentBean findParentComment(int articleID, int commentIndex);

    CommentBean findParentComment(int commentID);

    List<CommentBean> getRepliesOfComment(int articleID, int commentIndex, int offset, int limit);

    List<HashMap<String, Object>> getCommentsAndReplies(int articleID, int offset, int limit);

    int countCommentsOfArticle(int articleID);

    int countRepliesOfComment(int articleID, int commentIndex);
}
