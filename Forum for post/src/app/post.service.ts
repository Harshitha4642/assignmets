import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from './Post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  addNewPost(content: String): Observable<Post>{
    return this.http.post<Post>("http://localhost:8080/forum/create", content);
  }

  getPostById(id : number): Observable<Post>{
    console.log("sending request");
    return this.http.post<Post>(`http://localhost:8080/forum/fetch/${id}`, {} );
  }


}
