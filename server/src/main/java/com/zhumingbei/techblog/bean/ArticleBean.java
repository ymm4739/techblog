package com.zhumingbei.techblog.bean;

import lombok.Data;

import java.io.Serializable;

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
    private UserBean author;
    private Integer likedNums;
    private Integer collectedNums;
    private Integer commentNums;
    private Integer isPublished;
    private Integer isDeleted;
    private String thumbsTime;
    private String collectedTime;
    private String updatedTime;
    private String createdTime;
}
