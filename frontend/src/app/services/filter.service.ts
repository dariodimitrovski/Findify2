import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Post } from '../models/Post';

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  url = 'http://localhost:8080/api/posts'



  constructor(private http: HttpClient) { }

  filterPosts(formData: FormData): Observable<Post[]> {
    console.log("eve");
    const options = {
      params: {
        title: formData.get('title') as string,
        category: formData.get('category') as string,
        municipality: formData.get('municipality') as string,
        state: formData.get('state') as string,
        order: formData.get('order') as string
      }
    };
    return this.http.post<Post[]>(`${this.url}/filter`, null, options);
  }

}
