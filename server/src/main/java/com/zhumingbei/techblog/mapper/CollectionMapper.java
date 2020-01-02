package com.zhumingbei.techblog.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CollectionMapper {
    int update(int articleID, int userID, int isCollected);
    void insert(int articleID, int userID, int isCollected);
    List<Integer> selectByUserID(int userID);
}
