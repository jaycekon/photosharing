package com.sherlochao.service;

import com.sherlochao.model.Comment;

import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
public interface CommentService {

    Comment saveComment(Comment comment);

    List<Comment> findCommentsBySharedId(Integer sharedId);

    void delComment(Integer commentId);
}
