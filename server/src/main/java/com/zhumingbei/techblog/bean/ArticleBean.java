package com.zhumingbei.techblog.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleBean implements Serializable {
    private static final Long serialVersionID = 1L;
    private Integer id;
    private String title;
    private String content;
    private String html;
    private String summary;
    private String summaryImage;
    private Integer authorID;
    private Integer likedNums;
    private Integer collectedNums;
    private Integer commentNums;
    private Integer isPublished;
    private Integer isDeleted;
    private String thumbsTime;
    private String collectedTime;
    private String commentTime;
    private String updatedTime;
    private String createdTime;

    private UserBean author;
    private List<CommentBean> comments;
}
