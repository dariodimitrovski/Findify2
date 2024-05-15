import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Municipality } from '../models/Municipality';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MunicipalityService {

  constructor(private http: HttpClient) { }

  getMunicipalities(): Observable<Municipality[]> {
    return this.http.get<Municipality[]>('http://localhost:8080/api/municipalities');
  }
}