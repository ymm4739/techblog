package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.CommentBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    void insert(int userID, int articleID, int responseID, String content);

    CommentBean selectByID(int commentID);

    void update(CommentBean commentBean);

    List<CommentBean> selectByArticleID(int articleID);

}
