package com.zhumingbei.techblog.bean;

import lombok.Data;

import java.io.Serializable;
@Data
public class CommentBean implements Serializable {
    private static final Long serialVersionID = 1L;
    private Integer id;
    private Integer userID;
    private Integer responseID;
    private Integer articleID;
    private String content;
    private Integer isDeleted;
    private String updatedTime;
    private String createdTime;
    private UserBean commenter;
    private UserBean response;

}
