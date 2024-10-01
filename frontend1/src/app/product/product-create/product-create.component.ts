import { Component } from '@angular/core';
import { ProductService } from '../../services/product/product-service.service';
import { Product } from '../../model/product.model';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.scss'],
})
export class ProductCreateComponent {
  newProduct: Product = {
    id: 0, // Valor inicial, puede ser autogenerado en el backend
    mark: '',
    price: 0
  };

  constructor(private productService: ProductService) { }

  onSubmit() {
    this.productService.createProduct(this.newProduct).subscribe(
      response => {
        console.log('producto creado:', response);
        // Aquí puedes agregar lógica adicional, como redirigir o mostrar un mensaje
      },
      error => {
        console.error('Error al crear el producto', error);
      }
    );
  }
}
