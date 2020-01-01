package com.zhumingbei.techblog.mapper;

import java.util.List;

public interface CollectionMapper {
    int update(int articleID, int userID, int isCollected);
    void insert(int articleID, int userID, int isCollected);
    List<Integer> selectByUserID(int userID);
}
