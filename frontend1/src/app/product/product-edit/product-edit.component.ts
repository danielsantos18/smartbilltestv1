// edit-user.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService} from '../../services/product/product-service.service';
import { Product } from '../../model/product.model';

@Component({
  selector: 'app-edit-product',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.scss']
})
export class ProductEditComponent implements OnInit {
  productId!: number;
  product: Product = { id: 0, mark: '', price: 0 }; // Inicializa el usuario

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadUser();
  }

  loadUser(): void {
    this.productService.getProductById(this.productId).subscribe(
      (data) => {
        this.product = data;
      },
      (error) => {
        console.error('Error al cargar el producto:', error);
      }
    );
  }

  onSubmit(): void {
    this.productService.updateProduct(this.productId, this.product).subscribe(
      () => {
        console.log('Producto actualizado con éxito');
        this.router.navigate(['/dashboard/product/list']); // Redirige a la lista
      },
      (error) => {
        console.error('Error al actualizar el producto:', error);
      }
    );
  }
}

