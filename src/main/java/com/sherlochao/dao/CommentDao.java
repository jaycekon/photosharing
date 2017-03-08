package com.sherlochao.dao;

import com.sherlochao.model.Comment;

import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
public interface CommentDao {

    Comment saveComment(Comment comment);

    Integer updateComment(Comment comment);

    List<Comment> findCommentsBySharedId(Integer sharedId);

    void delComment(Integer commentId);
}
