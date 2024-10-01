import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private baseURL = 'http://localhost:8080/api/auth/login'; // Cambia según sea necesario

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<{ token: string; roles: { rolId: number; name: string }[]; username: string }> {
    const body = { username, password };
    return this.http.post<{ token: string; roles: { rolId: number; name: string }[]; username: string }>(this.baseURL, body).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('roles', JSON.stringify(response.roles)); // Almacena roles
        localStorage.setItem('username', response.username); // Almacena el nombre de usuario
      }),
      catchError(err => {
        console.error('Error de autenticación:', err);
        return throwError(err);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token'); // Elimina el token al cerrar sesión
    localStorage.removeItem('username'); // Elimina el username al cerrar sesión
    localStorage.removeItem('roles'); // Elimina los roles al cerrar sesión
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token'); // Verifica si el usuario está autenticado
  }

  getRoles(): { rolId: number; name: string }[] {
    return JSON.parse(localStorage.getItem('roles') || '[]'); // Obtiene los roles almacenados
  }
}
