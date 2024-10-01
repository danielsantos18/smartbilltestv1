import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { LoginService } from '../services/auth/login-service.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor(private loginService: LoginService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const requiredRoles = route.data['roles'] as Array<string>; // Roles requeridos desde las rutas
        const userRoles = this.loginService.getRoles().map(role => role.name); // Obtener solo los nombres de los roles

        console.log('Roles requeridos:', requiredRoles);
        console.log('Roles del usuario:', userRoles);

        const hasAccess = requiredRoles.some(role => userRoles.includes(role));

        if (!hasAccess) {
            alert('No tienes permisos'); // Redirigir a una página de acceso denegado
            return false;
        }

        return true;
    }

}
