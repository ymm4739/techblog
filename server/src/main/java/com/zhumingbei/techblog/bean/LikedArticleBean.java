package com.zhumingbei.techblog.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikedArticleBean implements Serializable {
    private static final Long serialVersionID = 1L;
    private Integer id;
    private Integer userID;
    private Integer articleID;
    private Integer isLiked;
}
