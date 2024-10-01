import { Component } from '@angular/core';
import { LoginService } from '../services/auth/login-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private loginService: LoginService, private router: Router) { }

  login() {
    this.loginService.login(this.username, this.password).subscribe(
      response => {
        console.log('Inicio de sesión exitoso:', response);

        // Almacena el token y los roles
        const roles = this.loginService.getRoles();

        // Verificar si el usuario tiene el rol de 'ADMIN'
        const isAdmin = roles.some(role => role.name === 'ADMIN');

        if (isAdmin) {
          this.router.navigate(['/dashboard/product']);
        } else {
          this.router.navigate(['/dashboard/product/list']);
        }
      },
      error => {
        console.error('Error en el inicio de sesión', error);
        this.errorMessage = 'Credenciales incorrectas. Inténtalo de nuevo.';
      }
    );
  }
}
