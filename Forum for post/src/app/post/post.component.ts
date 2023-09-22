import { Component, OnInit } from '@angular/core';
import { PostService } from '../post.service';
import { Post } from '../Post';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent {
  content: String = "";
  post?: Post;
  id : number = 0;

  constructor(private postService: PostService) { 
    //this.post = {id: 0, content: ''};
  }
  createPost(): void{
    console.log("post created"); 
    console.log(this.content);
    this.postService.addNewPost(this.content).subscribe(res => this.post = res);
  }

  fetchPost(): void{
    console.log(this.id);
    this.postService.getPostById(this.id).subscribe(res => {
      if(res === null)
        this.post = {postId: -100, postContent: "No such post"};
      else
        this.post = res;
      console.log(this.post)
    });
    
    console.log(this.post ? this.post.postId : '');
    //return this.post;
  }
}
