package com.angular.forum.Repository;

import org.springframework.data.repository.CrudRepository;

import com.angular.forum.Entity.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {

}
