package com.sunsky.manytoone.dao;

import com.sunsky.onetomany.entity.Post;

public interface PostDao {
    public Post findByPostId(int postId);
}
