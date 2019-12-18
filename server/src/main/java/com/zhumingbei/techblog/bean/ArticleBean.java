package com.zhumingbei.techblog.bean;

import lombok.Data;

@Data
public class ArticleBean {
    public static final Long serialVersionID = 1L;
    public Integer id;
    public String title;
    public String content;
    public Integer authorID;
    public UserBean author;
    public Integer likedNums;
    public Integer collectedNums;
    public Integer recommendedNums;
    public Integer commentNums;
    public String updatedTime;
    public String createdTime;
}
