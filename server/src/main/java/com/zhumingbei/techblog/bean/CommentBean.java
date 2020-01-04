package com.zhumingbei.techblog.bean;

import lombok.Data;

import java.io.Serializable;
@Data
public class CommentBean implements Serializable {
    private static final Long serialVersionID = 1L;
    private Integer id;
    private Integer userID;
    private Integer commentIndex;
    private Integer articleID;
    private Integer answerID;
    private Integer replyNums;
    private Integer likedNums;
    private String content;
    private Integer isDeleted;
    private Integer isParentDeleted;
    private String updatedTime;
    private String createdTime;
    private UserBean user;
    private UserBean answer;

}
