package com.demo.sharon.dao;

import com.demo.sharon.pojo.Comment;
import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Comment record);

    Comment selectByPrimaryKey(String id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);
}