package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.CommentBean;
import com.zhumingbei.techblog.mapper.CommentMapper;
import com.zhumingbei.techblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int comment(int userID, int articleID, int commentIndex, int answerID, String content) {
        CommentBean commentBean = new CommentBean();
        commentBean.setUserID(userID);
        commentBean.setArticleID(articleID);
        commentBean.setAnswerID(answerID);
        commentBean.setContent(content);
        if (commentIndex <= 0) {
            commentIndex = commentMapper.getMaxCommentIndexOfArticle(articleID) + 1;
        }
        commentBean.setCommentIndex(commentIndex);
        commentMapper.insert(commentBean);
        return commentBean.getId();
    }

    @Override
    public void comment(CommentBean commentBean) {
        commentMapper.insert(commentBean);
    }

    @Override
    public CommentBean findByID(int commentID) {
        return commentMapper.selectByID(commentID);
    }

    @Override
    public void update(CommentBean commentBean) {
        commentMapper.update(commentBean);
    }

    @Override
    public List<CommentBean> getCommentsOfArticle(int articleID, int offset, int limit) {
        return commentMapper.selectCommentsByArticleID(articleID, offset, limit);
    }

    @Override
    public int deleteByParent(int articleID, int commentIndex) {
        return commentMapper.deleteByParent(articleID, commentIndex);
    }

    @Override
    public CommentBean findParentComment(int articleID, int commentIndex) {
        return commentMapper.selectParent(articleID, commentIndex);
    }

    @Override
    public CommentBean findParentComment(int commentID) {
        CommentBean commentBean = findByID(commentID);
        if (commentBean == null) {
            return null;
        }
        return findParentComment(commentBean.getArticleID(), commentBean.getCommentIndex());
    }

    @Override
    public List<CommentBean> getRepliesOfComment(int articleID, int commentIndex, int offset, int limit) {
        return commentMapper.selectRepliesByParent(articleID, commentIndex, offset, limit);
    }

    @Override
    public List<HashMap<String, Object>> getCommentsAndReplies(int articleID, int offset, int limit) {
        List<CommentBean> comments = getCommentsOfArticle(articleID, offset, limit);
        List<HashMap<String, Object>> result = new ArrayList<>();

        for (CommentBean comment : comments) {
            int start = 0;
            int size = 2;
            HashMap<String, Object> item = new HashMap<>();
            item.put("comment", comment);
            List<CommentBean> replies = getRepliesOfComment(articleID, comment.getCommentIndex(), start, size);
            item.put("replies", replies);
            result.add(item);

        }
        return result;
    }

    @Override
    public int countCommentsOfArticle(int articleID) {
        return commentMapper.countCommentsOfArticle(articleID);
    }

    @Override
    public int countRepliesOfComment(int articleID, int commentIndex) {
        return commentMapper.countRepliesOfComment(articleID, commentIndex);
    }
}
