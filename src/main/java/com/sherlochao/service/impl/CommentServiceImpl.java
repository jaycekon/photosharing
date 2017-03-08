package com.sherlochao.service.impl;

import com.sherlochao.dao.CommentDao;
import com.sherlochao.model.Comment;
import com.sherlochao.service.CommentService;
import com.sherlochao.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Transactional
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;

    @Override
    public Comment saveComment(Comment comment) {
        String time = DateUtils.getDate1();
        comment.setCommentCreateTime(time);
        return commentDao.saveComment(comment);
    }

    @Override
    public List<Comment> findCommentsBySharedId(Integer sharedId) {
        return commentDao.findCommentsBySharedId(sharedId);
    }

    @Override
    public void delComment(Integer commentId) {
        commentDao.delComment(commentId);
    }


}
