import { Injectable } from '@angular/core';
import { User } from '../models/User';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8080/api'

  constructor(private http: HttpClient) { }

  currentUser: User | undefined

  setCurrentUser(user: User) {
    this.currentUser = user;
  }

  getCurrentUser(): User {
    return this.currentUser!!;
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.url}/users/all`)
  }

  deleteUser(userId: number): Observable<any> {
    return this.http.delete<User>(`${this.url}/users/${userId}`)
  }
  
  // updateUser(userId: number, userData: any): Observable<User> {
  //   return this.http.put<User>(`${this.url}/users/${userId}`, userData)
  // }

  updateProfile(formData: FormData): Observable<User>{
    return this.http.post<User>(`${this.url}/edit-profile`, formData)
  }

  getUserImage(id: number){
    console.log("eve slika")
    return this.http.get(`${this.url}/mia/image/${id}`, {
      responseType: 'blob'
    })
  }

}
