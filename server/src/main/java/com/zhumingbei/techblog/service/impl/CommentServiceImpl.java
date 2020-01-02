package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.CommentBean;
import com.zhumingbei.techblog.mapper.CommentMapper;
import com.zhumingbei.techblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void comment(int userID, int articleID, int responseID, String content) {
        commentMapper.insert(userID, articleID, responseID, content);
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
    public List<CommentBean> getCommentsOfArticle(int articleID) {
        return commentMapper.selectByArticleID(articleID);
    }
}
