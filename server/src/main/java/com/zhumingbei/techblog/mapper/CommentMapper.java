package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.CommentBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int insert(CommentBean commentBean);

    CommentBean selectByID(int commentID);

    void update(CommentBean commentBean);

    List<CommentBean> selectCommentsByArticleID(int articleID, int offset, int limit);

    List<CommentBean> selectRepliesByParent(int articleID, int commentIndex, int offset, int limit);

    int countCommentsOfArticle(int article);

    int deleteByParent(int articleID, int commentIndex);

    CommentBean selectParent(int articleID, int commentIndex);

    int countRepliesOfComment(int articleID, int commentIndex);

    int getMaxCommentIndexOfArticle(int articleID);
}
