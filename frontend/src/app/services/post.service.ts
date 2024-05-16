import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../models/Post';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  url = 'http://localhost:8080/api'
  postCoordinates: number[] = []


  constructor(private http: HttpClient) {}

  addCoordinates(coordinates: number[]) {
    this.postCoordinates = coordinates
  }

  getCoordinates(): number[] {
    return this.postCoordinates
  }

  addPost(formData: FormData): Observable<Post> {
    console.log("Posted.")
    return this.http.post<Post>(`${this.url}/new-post`, formData)
  }

  getLostItems(page: number = 0, size: number = 10): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}/lost-items?page=${page}&size=${size}`)
  }

  getLostItemsSize(): Observable<any>{
    return this.http.get<any>(`${this.url}/posts/size`)
  }

  getFoundItems(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}/found-items`)
  }
  getPendingPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}/pending-posts`)
  }

  getPostById(id: number): Observable<Post | undefined> {
    return this.http.get<Post>(`${this.url}/posts/${id}`)
  }

  getPostsByUserId(id: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}/posts/user/${id}`)
  }

  approvePost(post: Post): Observable<Post> {
    return this.http.put<Post>(`${this.url}/posts/${post.id}`, post)
  }

  declinePost(id: number): Observable<any> {
    return this.http.delete<Post>(`${this.url}/posts/${id}`)
  }

  getPostImage(postId: number){
    return this.http.get(`${this.url}/${postId}/image`, {
      responseType: 'blob'
    })
  }

}
