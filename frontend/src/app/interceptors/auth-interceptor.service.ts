import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor() { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const jwtToken = localStorage.getItem("jwtToken");

    // const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNaWEiLCJleHAiOjE3MTgyMzc3MDksImlhdCI6MTcxNTY0NTcwOSwidXNlcklkIjoxLCJlbWFpbCI6Im1pYUBhZG1pbi5jb20ifQ.f7L47kTJBb6eIwnDn4h4cxJaPeNJt8p-1onhmpJUb7A"

    if (jwtToken) {
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${jwtToken}`
        }
      });

      return next.handle(cloned)
    }
    else {
      return next.handle(req)
    }
  }
}
