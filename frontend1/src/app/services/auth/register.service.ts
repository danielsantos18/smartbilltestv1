import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from '../../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private baseURL = 'http://localhost:8080/api/auth'; // Cambia según sea necesario

  constructor(private http: HttpClient) {}

  register(userData: User): Observable<any> {
    return this.http.post<any>(`${this.baseURL}/register`, userData, { responseType: 'text' as 'json' }).pipe(
      tap(response => {
        console.log('Registro exitoso:', response);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
}
