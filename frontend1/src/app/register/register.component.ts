import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from '../services/auth/register.service';
import { User } from '../model/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(private fb: FormBuilder, private registerService: RegisterService) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      firstname: ['', Validators.required], // Campo añadido
      lastname: ['', Validators.required],   // Campo añadido
      phone: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]], // Validación de email
      birthdate: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.loading = true;
      const userData: User = this.registerForm.value; // Captura todos los campos
      this.registerService.register(userData).subscribe({
        next: () => {
          this.loading = false;
          // Manejo de éxito (redirigir o mostrar mensaje)
        },
        error: (err) => {
          this.loading = false;
          this.error = 'Error en el registro: ' + err.message; // Manejo de errores
        }
      });
    }
  }
}
