package com.angular.forum.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angular.forum.Entity.Post;
import com.angular.forum.Repository.PostRepository;

import io.micrometer.core.ipc.http.HttpSender.Response;

@RestController
@RequestMapping("/forum")
@CrossOrigin
public class ForumController {
	
	@Autowired
	PostRepository postRepository;
	
	@PostMapping("/create")
	public Post createPost(@RequestBody String content)
	{
		Post post = new Post();
		System.out.println("I am working, here is the content" +content);
		post.setPostContent(content.toString());
		postRepository.save(post);
		return post;
	}
	
	@PostMapping("/fetch/{id}")
	public ResponseEntity<?> fetchPost(@PathVariable("id") int id) 
	{
		System.out.println("hello");
		System.out.println("I am the post id: "+id);
		try {
		Optional<Post> post = Optional.ofNullable(postRepository.findById(id).get());
		System.out.println(post.get().getPostId());
		System.out.println(post.get().getPostContent());
		return ResponseEntity.ok(post.get());
		} catch(NoSuchElementException e)
		{
			return null;
		}
	}
}
