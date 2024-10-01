import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token'); // Obtén el token}

    const isLoginOrRegister = request.url.includes('/api/auth/login') || request.url.includes('/api/auth/register');

   if (token && !isLoginOrRegister) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}` // Agrega el token al encabezado
        }
      });
    }

    return next.handle(request);
  }
}
