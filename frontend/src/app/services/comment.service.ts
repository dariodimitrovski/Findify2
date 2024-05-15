import { Injectable } from '@angular/core';
import { Comment } from '../models/Comment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  url = 'http://localhost:8080/api'

  addComment(form: FormData): Observable<Comment> {
    return this.http.post<Comment>(`${this.url}/comments`, form)
  }

  getCommentsForPost(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.url}/comments/${postId}`)
  }
}
