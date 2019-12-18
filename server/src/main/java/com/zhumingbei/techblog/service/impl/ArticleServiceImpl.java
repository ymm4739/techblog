package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.mapper.ArticleMapper;
import com.zhumingbei.techblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<ArticleBean> getAll() {
        return articleMapper.selectAll();
    }
    @Override
    public int create(ArticleBean article) {
        return articleMapper.insert(article);
    }

    @Override
    public ArticleBean findByID(int articleID) {
        return articleMapper.selectByID(articleID);
    }
}
