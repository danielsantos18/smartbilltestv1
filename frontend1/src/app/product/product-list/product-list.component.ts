import { Component, OnInit } from '@angular/core';
import { Product } from '../../model/product.model';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product/product-service.service';
import { LoginService } from '../../services/auth/login-service.service'; // Asegúrate de importar el LoginService

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private router: Router,
    private loginService: LoginService // Inyectar el servicio de login
  ) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe(
      (data: Product[]) => {
        this.products = data;
      },
      (error) => {
        console.error('Error fetching products', error);
      }
    );
  }

  editProduct(product: Product): void {
    this.router.navigate(['/dashboard/product/edit', product.id]);
  }

  deleteProduct(product: Product): void {
    // Verificar si el usuario tiene el rol de admin
    const isAdmin = this.loginService.getRoles().some(role => role.name === 'ADMIN');

    if (!isAdmin) {
      alert('No tienes permiso para eliminar productos.');
      return; // Si no es admin, salir del método
    }

    if (confirm(`¿Estás seguro de que deseas eliminar ${product.mark}?`)) {
      this.productService.deleteProduct(product.id).subscribe(
        () => {
          this.loadProducts(); // Recargar la lista después de eliminar
        },
        (error) => {
          console.error('Error deleting Product', error);
        }
      );
    }
  }
}
